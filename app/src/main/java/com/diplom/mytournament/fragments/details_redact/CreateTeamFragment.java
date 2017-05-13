package com.diplom.mytournament.fragments.details_redact;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.diplom.mytournament.MyTournamentDatabaseHelper;
import com.diplom.mytournament.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTeamFragment extends Fragment {

    private String kindOfSport;

    public static MyTournamentDatabaseHelper dbHelper;

    @BindView(R.id.submit_button)
    Button submitButton;

    @BindView(R.id.edit_team_name)
    EditText nameEdit;

    @BindView(R.id.team_info_edit)
    EditText infoEdit;


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
                dbHelper.insertTeam(db, nameEdit.getText().toString(), kindOfSport, R.drawable.ic_menu_gallery,
                        infoEdit.getText().toString());
                Snackbar.make(rootView, "Команда создана!", Snackbar.LENGTH_LONG).show();

                infoEdit.setText("");
                nameEdit.setText("");
            }

        });
        return rootView;
    }

}
