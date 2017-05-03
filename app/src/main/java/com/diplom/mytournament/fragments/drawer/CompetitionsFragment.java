package com.diplom.mytournament.fragments.drawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.adapters.CompetitionsReсViewAdapter;
import com.diplom.mytournament.models.Competition;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitionsFragment extends Fragment {


    private List<Competition> competitionList = new ArrayList<>();

    private RecyclerView recyclerView;

    private CompetitionsReсViewAdapter rAdapter;

    public CompetitionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_competitions, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.competitions_recycler_view);
        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
        competitionList = qh.getAllCompetition();
        rAdapter = new CompetitionsReсViewAdapter(this, competitionList);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);

        return rootView;


    }


}
