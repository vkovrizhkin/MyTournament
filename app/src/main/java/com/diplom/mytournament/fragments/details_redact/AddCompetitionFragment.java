package com.diplom.mytournament.fragments.details_redact;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.diplom.mytournament.MyTournamentDatabaseHelper;
import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Competition;
import com.diplom.mytournament.models.Format;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCompetitionFragment extends Fragment {

    private String[] sports = new String[]{"Футбол", "Баскетбол", "Воллейбол"};

    private List<Format> formatList = new ArrayList<>();

    public static MyTournamentDatabaseHelper dbHelper;

    private static final int REQUEST = 1;

    private FirebaseAuth firebaseAuth;

    private Uri selectedImage;

    private String imageUrl;

    private DatabaseReference databaseReference;

    private StorageReference mStorageRef;

    String logoUri;

    @BindView(R.id.competition_title_edit)
    EditText title;

    @BindView(R.id.kind_of_sport_spinner)
    Spinner sport;

    @BindView(R.id.format_spinner)
    Spinner format;

    @BindView(R.id.competition_date)
    DatePicker date;

    @BindView(R.id.submit_button)
    Button submit;

    @BindView(R.id.comp_info_edit)
    EditText info;

    @BindView(R.id.comp_logo)
    ImageView image;

    @BindView(R.id.add_comp_logo_button)
    Button addLogo;


    public AddCompetitionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        final View rootView = inflater.inflate(R.layout.fragment_add_competition, container, false);
        ButterKnife.bind(this, rootView);
        dbHelper = new MyTournamentDatabaseHelper(getContext());
        final MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
        ArrayAdapter<String> aAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout
                .simple_spinner_item, sports);
        aAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sport.setAdapter(aAdapter);

        sport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kindOfSport = "";
                switch (position) {
                    case 0:
                        kindOfSport = "football";
                        break;
                    case 2:
                        kindOfSport = "volleyball";
                        break;
                    case 1:
                        kindOfSport = "basketball";
                        break;
                    default:
                }

                formatList = qh.getFormatsBySport(kindOfSport);
                ArrayAdapter<Format> fAdapter = new ArrayAdapter<Format>(parent.getContext(), android.R.layout
                        .simple_spinner_item, formatList);
                fAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                format.setAdapter(fAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SQLiteDatabase db = dbHelper.getWritableDatabase();
                // dbHelper.insertCompetition(db, title.getText().toString(), "league", f.getId(), dateString,
                //    info.getText().toString(), logoUri);
               // final String timestamp = new java.sql.Timestamp(System.currentTimeMillis()).toString();
                final long timestamp = System.currentTimeMillis();
                StorageReference riversRef = mStorageRef.child("CompetitionsLogos/logo_" + timestamp + ".jpg");

                riversRef.putFile(selectedImage).
                        addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                imageUrl = downloadUrl.toString();
                                Format f = (Format) format.getSelectedItem();
                                String dateString = Integer.toString(date.getDayOfMonth()) + ":" + Integer.toString(date.getMonth());
                                Competition competition1 = new Competition(title.getText().toString(), timestamp, f.getId(),
                                        info.getText().toString(), dateString, "league", imageUrl);
                                databaseReference.child(user.getUid()).child("Competitions").child("Comp_"+Long.toString(timestamp)).setValue(competition1);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });



                Snackbar.make(rootView, "Соревнование создано!", Snackbar.LENGTH_LONG).show();

            }
        });

        addLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, REQUEST);
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap img = null;

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                logoUri = selectedImage.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(img);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
