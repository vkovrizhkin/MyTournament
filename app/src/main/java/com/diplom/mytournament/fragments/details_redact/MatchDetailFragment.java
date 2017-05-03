package com.diplom.mytournament.fragments.details_redact;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diplom.mytournament.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchDetailFragment extends Fragment {

    @BindView(R.id.timer)
    TextView timer;

    private int timeMinutes;

    private int periods;

    private int miliseconds = 0;

    private boolean running;

    public MatchDetailFragment(int timeMinutes, int periods) {
        this.timeMinutes = timeMinutes;
        this.periods = periods;
        this.miliseconds = timeMinutes * 60 * 1000;
    }


    public MatchDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_detail, container, false);
    }

    private void runTimer() {
        // final TextView textView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = miliseconds / 360000;
                int minutes = (miliseconds % 360000) / 6000;
                int secs = (miliseconds % 6000) / 100;
                int mili = miliseconds % 100;

                String time = String.format("%d:%02d:%02d:%02d", hours, minutes, secs, mili);
                timer.setText(time);
                while (miliseconds >= 0) {
                    if (running) {
                        miliseconds--;
                    }
                }

                handler.postDelayed(this, 10);
            }
        });


    }

}
