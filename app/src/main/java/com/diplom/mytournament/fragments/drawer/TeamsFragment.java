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
import com.diplom.mytournament.activities.CompetitionActivity;
import com.diplom.mytournament.adapters.TeamsRecViewAdapter;
import com.diplom.mytournament.fragments.details_redact.AddTeamFragment;
import com.diplom.mytournament.models.Competition;
import com.diplom.mytournament.models.Format;
import com.diplom.mytournament.models.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamsFragment extends Fragment {

    private long competitionId;

    private RecyclerView recyclerView;

    private TeamsRecViewAdapter rAdapter;

    private Format format;

    private Competition competition;

    private List<Team> teamList = new ArrayList<>();

    private DatabaseReference ref;

    private FirebaseAuth mAuth;

    private FirebaseUser user;

    public TeamsFragment(long competitionId) {
        this.competitionId = competitionId;
    }


    public TeamsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_teams, container, false);
        getActivity().setTitle(R.string.teams);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.teams_recycler_view);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.teams_fab);
        fab.attachToRecyclerView(recyclerView);
        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child(user.getUid());

        if (mAuth.getCurrentUser() != null) {
            DatabaseReference ref2 = ref.child("Competitions").orderByChild("id").equalTo(Long.toString(competitionId)).getRef();
            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<Map<String, Competition>> t = new GenericTypeIndicator<Map<String, Competition>>() {};
                    Map<String, Competition> map = dataSnapshot.getValue(t);
                    if (map!=null){
                        for (String key : map.keySet()) {
                           Competition  competition1 = map.get(key);
                            if(competition1.getId()==competitionId){
                                competition = competition1;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else{

            competition = qh.getCompetitionById(competitionId);

            teamList = qh.getTeamsByCompetitionId(competitionId);
            CompetitionActivity activity = (CompetitionActivity) getActivity();
            rAdapter = new TeamsRecViewAdapter(teamList, competitionId, activity, getFragmentManager());
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(rAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

        }

/*        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);*/
        format = qh.getFormatById(competition.getFormat());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddTeamFragment(format.getKindOfSport());
                getFragmentManager().beginTransaction().replace(R.id.competition_frame_layout, fragment)
                        .addToBackStack(null).commit();
            }
        });

        return rootView;


    }

}
