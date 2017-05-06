package com.diplom.mytournament.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.R;
import com.diplom.mytournament.adapters.TeamPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {


    public TeamFragment() {
        // Required empty public constructor
    }

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    TeamPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_team, container, false);
        mDemoCollectionPagerAdapter =
                new TeamPagerAdapter(
                        getFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.team_pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        return rootView;
    }

}
