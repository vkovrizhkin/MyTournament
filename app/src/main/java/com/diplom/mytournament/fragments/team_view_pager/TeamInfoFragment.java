package com.diplom.mytournament.fragments.team_view_pager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diplom.mytournament.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamInfoFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    private int teamId;

    public TeamInfoFragment() {
        // Required empty public constructor
    }

    public TeamInfoFragment(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_team_info, container, false);
        return rootView;
        // Inflate the layout for this fragment

    }

}
