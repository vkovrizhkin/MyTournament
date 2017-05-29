package com.diplom.mytournament.fragments.details_redact;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.diplom.mytournament.MyTournamentDatabaseHelper;
import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMatchFragment extends Fragment {

    private int competitionId;

    private MyTournamentQueryHelper qh;

    private MyTournamentDatabaseHelper dbHelper;

    private List<Team> teams = new ArrayList<>();

    @BindView(R.id.enter_stage)
    EditText stage;

    @BindView(R.id.select_team1)
    Spinner team1;

    @BindView(R.id.select_team2)
    Spinner team2;

    @BindView(R.id.match_submit_button)
    Button submitButton;

    @BindView(R.id.match_date)
    DatePicker date;

    @BindView(R.id.enter_match_place)
    EditText place;

    @BindView(R.id.time_picker)
    TimePicker timePicker;

    public AddMatchFragment(int competitionId) {
        this.competitionId = competitionId;
    }

    public AddMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_match, container, false);
        ButterKnife.bind(this, rootView);
        timePicker.setIs24HourView(true);

        qh = new MyTournamentQueryHelper(getContext());
        dbHelper = new MyTournamentDatabaseHelper(getContext());

        teams = qh.getTeamsByCompetitionId(competitionId);
        ArrayAdapter<Team> adapter = new ArrayAdapter<Team>(getContext(), android.R.layout
                .simple_spinner_item, teams);
        team1.setAdapter(adapter);
        team2.setAdapter(adapter);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Team teamH = (Team)team1.getSelectedItem();
                Team teamA = (Team)team2.getSelectedItem();
                String dateString = date.getDayOfMonth()+"."+date.getMonth()+1+
                        "."+date.getYear()+"  ";
                dateString+=timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute();
                dbHelper.insertMatch(db, competitionId, dateString, place.getText().toString(),
                        stage.getText().toString(),teamH.getId(), teamA.getId(), 0 );
                Snackbar.make(rootView, "Матч создан!", Snackbar.LENGTH_LONG).show();
            }
        });




        return rootView;
    }

}
