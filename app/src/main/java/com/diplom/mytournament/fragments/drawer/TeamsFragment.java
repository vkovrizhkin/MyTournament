package com.diplom.mytournament.fragments.drawer;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.adapters.CompetitionsReсViewAdapter;
import com.diplom.mytournament.adapters.TeamsRecViewAdapter;
import com.diplom.mytournament.fragments.details_redact.AddTeamFragment;
import com.diplom.mytournament.models.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamsFragment extends Fragment {

    private int competitionId;

    private RecyclerView recyclerView;

    private TeamsRecViewAdapter rAdapter;

    private List<Team> teamList = new ArrayList<>();

    public TeamsFragment(int competitionId) {
        this.competitionId = competitionId;
    }


    public TeamsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teams, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.teams_recycler_view);

        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());

        teamList = qh.getTeamsByCompetitionId(competitionId);

        rAdapter = new TeamsRecViewAdapter(teamList);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.teams_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddTeamFragment();
                getFragmentManager().beginTransaction().replace(R.id.competition_frame_layout, fragment)
                        .addToBackStack(null).commit();
            }
        });

        return rootView;



    }

}
