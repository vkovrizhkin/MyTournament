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
import com.diplom.mytournament.adapters.StandingsRecViewAdapter;
import com.diplom.mytournament.models.Standing;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StandingsFragment extends Fragment {

    private StandingsRecViewAdapter rAdapter;

    private int competitionId;

    private List<Standing> standingList;

    private RecyclerView recyclerView;

    public StandingsFragment() {
        // Required empty public constructor
    }

    public StandingsFragment(int competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_standings, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.standings_rec_view);
        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
        standingList = qh.getStandingsByCompetitionId(competitionId);

        rAdapter = new StandingsRecViewAdapter(standingList, getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        return rootView;
    }

}
