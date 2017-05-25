package com.diplom.mytournament.fragments.details_redact;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diplom.mytournament.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMatchFragment extends Fragment {


    public AddMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_match, container, false);
    }

}
