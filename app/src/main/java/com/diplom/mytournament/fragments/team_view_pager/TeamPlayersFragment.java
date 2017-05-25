package com.diplom.mytournament.fragments.team_view_pager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.adapters.PlayersRecViewAdapter;
import com.diplom.mytournament.fragments.details_redact.AddPlayerFragment;
import com.diplom.mytournament.models.Player;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamPlayersFragment extends Fragment {

    private int teamId;

    private RecyclerView recyclerView;

    private List<Player> playerList;

    private PlayersRecViewAdapter rAdapter;

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
        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.players_fab);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.team_players_rec_view);
/*        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());

        playerList = qh.getPlayersByTeamId(teamId);
        rAdapter = new PlayersRecViewAdapter(playerList);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddPlayerFragment(teamId);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.competition_frame_layout, fragment).addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());

        playerList = qh.getPlayersByTeamId(teamId);
        rAdapter = new PlayersRecViewAdapter(playerList);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }
}
