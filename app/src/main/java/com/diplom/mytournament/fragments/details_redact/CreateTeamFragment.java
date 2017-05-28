package com.diplom.mytournament.fragments.details_redact;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.diplom.mytournament.MyTournamentDatabaseHelper;
import com.diplom.mytournament.R;

import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment {

    private String kindOfSport;

    private String logoUri;

    public static MyTournamentDatabaseHelper dbHelper;

    private static final int REQUEST = 1;

    @BindView(R.id.submit_button)
    Button submitButton;

    @BindView(R.id.edit_team_name)
    EditText nameEdit;

    @BindView(R.id.team_info_edit)
    EditText infoEdit;

    @BindView(R.id.team_logo)
    ImageView image;

    @BindView(R.id.add_team_logo_button)
    Button addLogo;


    public CreateTeamFragment() {
        // Required empty public constructor
    }

    public CreateTeamFragment(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_create_team, container, false);
        ButterKnife.bind(this, rootView);
        dbHelper =  new MyTournamentDatabaseHelper(getContext());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                dbHelper.insertTeam(db, nameEdit.getText().toString(), kindOfSport, logoUri,
                        infoEdit.getText().toString());
                Snackbar.make(rootView, "Команда создана!", Snackbar.LENGTH_LONG).show();

                infoEdit.setText("");
                nameEdit.setText("");
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
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap img = null;

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
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
