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
import com.diplom.mytournament.activities.MainActivity;
import com.diplom.mytournament.adapters.CompetitionsReсViewAdapter;
import com.diplom.mytournament.fragments.details_redact.AddCompetitionFragment;
import com.diplom.mytournament.models.Competition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompetitionsFragment extends Fragment {


    private List<Competition> competitionList = new ArrayList<>();

    private RecyclerView recyclerView;

    private CompetitionsReсViewAdapter rAdapter;

   // private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;

    private StorageReference mStorageRef;

    private FirebaseUser user;

    public CompetitionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_competitions, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.competitions_recycler_view);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.competitions_fab);
        fab.attachToRecyclerView(recyclerView);
        MainActivity activity = (MainActivity) getActivity();
        rAdapter = new CompetitionsReсViewAdapter(this, competitionList, activity);
        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());

      //  firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        if(mAuth.getCurrentUser()!=null){
            databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<Map<String, Competition>> t = new GenericTypeIndicator<Map<String, Competition>>() {};
                    Map<String, Competition> map = dataSnapshot.child("Competitions").getValue(t);
                    if (map!=null){
                        for (String key : map.keySet()) {
                            Competition c1 = map.get(key);
                            competitionList.add(c1);

                        }
                    }
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(rAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            competitionList = qh.getAllCompetition();
            rAdapter = new CompetitionsReсViewAdapter(this, competitionList, activity);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(rAdapter);
        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddCompetitionFragment();
                // Fragment fragment = new AddPlayerFragment();
                getFragmentManager().beginTransaction().replace(R.id.profile_frame_layout, fragment).addToBackStack(null).commit();
            }
        });


        return rootView;


    }


}
