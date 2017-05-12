package com.diplom.mytournament.fragments.details_redact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTeamsFragment extends Fragment {

    private String kindOfSport;

    private List<Team> teamList;

    private ArrayAdapter<Team> aAdapter;

    @BindView(R.id.teams_list)
    ListView listView;

    public SelectTeamsFragment() {
        // Required empty public constructor
    }

    public SelectTeamsFragment(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_teams, container, false);
        ButterKnife.bind(this, rootView);

        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
        teamList = qh.getTeamsBySport(kindOfSport);
        aAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_multiple_choice,
                teamList);
       // aAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        listView.setAdapter(aAdapter);

        return rootView;
    }

}
