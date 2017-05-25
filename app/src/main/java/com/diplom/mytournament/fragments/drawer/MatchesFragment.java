package com.diplom.mytournament.fragments.drawer;


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
import com.diplom.mytournament.adapters.MatchesRecViewAdapter;
import com.diplom.mytournament.fragments.details_redact.AddMatchFragment;
import com.diplom.mytournament.models.Competition;
import com.diplom.mytournament.models.Match;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    private int competitionId;

    private List<Match> matchList;

    private RecyclerView recyclerView;

    private MatchesRecViewAdapter rAdapter;

    public MatchesFragment() {
        // Required empty public constructor
    }


    public MatchesFragment(int competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_matches, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.matches_recycler_view);
        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());

        matchList = qh.getMatchesByCompetitionId(competitionId);
        Competition competition = qh.getCompetitionById(competitionId);
        int formatId = competition.getFormat();

        rAdapter = new MatchesRecViewAdapter(matchList, getFragmentManager(), formatId);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.matches_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddMatchFragment();
                getFragmentManager().beginTransaction().replace(R.id.competition_frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

}
