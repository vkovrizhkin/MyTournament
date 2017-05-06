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
public class TeamMatchesFragment extends Fragment {

    private int teamId;

    private int competitionId;

    public TeamMatchesFragment() {
        // Required empty public constructor
    }

    public TeamMatchesFragment(int teamId, int competitionId) {
        this.teamId = teamId;
        this.competitionId = competitionId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_team_matches, container, false);
        // Inflate the layout for this fragment
        return rootView;

    }
}
