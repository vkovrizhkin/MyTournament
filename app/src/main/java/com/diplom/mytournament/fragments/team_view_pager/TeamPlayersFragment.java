package com.diplom.mytournament.fragments.team_view_pager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamPlayersFragment extends Fragment {

    private int teamId;

    public TeamPlayersFragment() {
        // Required empty public constructor
    }

    public TeamPlayersFragment(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team_players, container, false);
        return rootView;
    }

}
