package com.diplom.mytournament.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.R;
import com.diplom.mytournament.adapters.MyTournamentPagerAdapter;
import com.diplom.mytournament.fragments.team_view_pager.TeamInfoFragment;
import com.diplom.mytournament.fragments.team_view_pager.TeamMatchesFragment;
import com.diplom.mytournament.fragments.team_view_pager.TeamPlayersFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {

    private MyTournamentPagerAdapter myTournamentPagerAdapter;

    private ViewPager mViewPager;

    private int teamId = 1;

    private int competitionId = 1;

    public TeamFragment() {
        // Required empty public constructor
    }

    public TeamFragment(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);

        myTournamentPagerAdapter = new MyTournamentPagerAdapter(getFragmentManager(), 3);
        myTournamentPagerAdapter.addFragment(new TeamMatchesFragment(teamId, competitionId), "МАТЧИ");
        myTournamentPagerAdapter.addFragment(new TeamPlayersFragment(teamId), "СОСТАВ");
        myTournamentPagerAdapter.addFragment(new TeamInfoFragment(teamId), "ИНФОРМАЦИЯ");

        mViewPager = (ViewPager) rootView.findViewById(R.id.team_pager);
        mViewPager.setAdapter(myTournamentPagerAdapter);
        return rootView;
    }

}
