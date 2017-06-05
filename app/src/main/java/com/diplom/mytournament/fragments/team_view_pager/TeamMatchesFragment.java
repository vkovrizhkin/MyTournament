package com.diplom.mytournament.fragments.team_view_pager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.activities.CompetitionActivity;
import com.diplom.mytournament.adapters.MatchesRecViewAdapter;
import com.diplom.mytournament.models.Competition;
import com.diplom.mytournament.models.Match;
import com.diplom.mytournament.models.Team;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamMatchesFragment extends Fragment {

    private int teamId;

    private long competitionId;

    private RecyclerView recyclerView;

    private MatchesRecViewAdapter rAdapter;

    private List<Match> matchList;

    public TeamMatchesFragment() {
        // Required empty public constructor
    }

    public TeamMatchesFragment(int teamId, long competitionId) {
        this.teamId = teamId;
        this.competitionId = competitionId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_team_matches, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.team_matches_rec_view);

        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
        Team team = qh.getTeamById(teamId);
        getActivity().setTitle(team.getName());
        matchList = qh.getMatchesByTeamId(teamId, competitionId);
        Competition competition = qh.getCompetitionById(competitionId);
        int formatId = competition.getFormat();
        CompetitionActivity activity = (CompetitionActivity) getActivity();
        rAdapter = new MatchesRecViewAdapter(matchList, getFragmentManager(), formatId, activity);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        // Inflate the layout for this fragment
        return rootView;

    }
}
