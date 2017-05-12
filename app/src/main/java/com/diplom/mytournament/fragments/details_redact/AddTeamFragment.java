package com.diplom.mytournament.fragments.details_redact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.R;
import com.diplom.mytournament.adapters.MyTournamentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTeamFragment extends Fragment {

    private MyTournamentPagerAdapter pAdapter;

    private String kindOfSport;

    public AddTeamFragment() {
        // Required empty public constructor
    }

    public AddTeamFragment(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_team, container, false);
        pAdapter = new MyTournamentPagerAdapter(getFragmentManager(), 2);
        Fragment createTeamFragment = new CreateTeamFragment(kindOfSport);
        Fragment selectTeamFragment = new SelectTeamsFragment();
        pAdapter.addFragment(selectTeamFragment, getString(R.string.select_teams));
        pAdapter.addFragment(createTeamFragment, getString(R.string.new_team));
        ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.team_add_pager);
        viewPager.setAdapter(pAdapter);

        return rootView;
    }

}
