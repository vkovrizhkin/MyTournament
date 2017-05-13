package com.diplom.mytournament.fragments.details_redact;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.diplom.mytournament.MyTournamentDatabaseHelper;
import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.activities.CompetitionActivity;
import com.diplom.mytournament.activities.MainActivity;
import com.diplom.mytournament.models.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTeamsFragment extends Fragment {

    private String kindOfSport;

    private List<Team> teamList;

    private ArrayAdapter<Team> aAdapter ;

    private List<Integer> selectedTeamsId = new ArrayList<>();

    public static MyTournamentDatabaseHelper dbHelper;

    @BindView(R.id.teams_list)
    ListView listView;

    @BindView(R.id.teams_selected_count)
    TextView countSelected;

    @BindView(R.id.submit_button)
    Button submitButton;

    public SelectTeamsFragment() {
        // Required empty public constructor
    }

    public SelectTeamsFragment(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_select_teams, container, false);
        ButterKnife.bind(this, rootView);
        dbHelper =  new MyTournamentDatabaseHelper(getContext());
        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
        teamList = qh.getTeamsBySport(kindOfSport);
        aAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_multiple_choice,
                teamList);
       // aAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        listView.setAdapter(aAdapter);
        countSelected.setText(getString(R.string.selected_teams_count, selectedTeamsId.size()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTeamsId.clear();
                SparseBooleanArray chosen = ((ListView) parent).getCheckedItemPositions();
                for (int i = 0; i < chosen.size(); i++) {
                    // если пользователь выбрал пункт списка,
                    // то выводим его в TextView.
                    if (chosen.valueAt(i)) {
                        selectedTeamsId.add(teamList.get(chosen.keyAt(i)).getId());
                    }
                }
                countSelected.setText(getString(R.string.selected_teams_count, selectedTeamsId.size()));
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                CompetitionActivity activity = (CompetitionActivity) getActivity();

                for(int i = 0; i<selectedTeamsId.size(); i++){
                    dbHelper.insertStanding(db, selectedTeamsId.get(i), activity.getCompetitionId() );
                };
                Snackbar.make(rootView, getString(R.string.teams_selected_succesful, selectedTeamsId.size()) , Snackbar.LENGTH_LONG).show();

            }
        });

        return rootView;
    }

}
