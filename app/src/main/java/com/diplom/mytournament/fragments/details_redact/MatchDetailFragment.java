package com.diplom.mytournament.fragments.details_redact;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Format;
import com.diplom.mytournament.models.Match;
import com.diplom.mytournament.models.Team;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchDetailFragment extends Fragment {

    @BindView(R.id.timer)
    TextView timer;

    @BindView(R.id.md_team1_name)
    TextView team1Name;

    @BindView(R.id.md_team2_name)
    TextView team2Name;

    @BindView(R.id.md_team1_logo)
    ImageView team1Logo;

    @BindView(R.id.md_team2_logo)
    ImageView team2Logo;

    @BindView(R.id.md_scores1)
    TextView scores1;

    @BindView(R.id.md_scores2)
    TextView scores2;

    @BindView(R.id.start_button)
    ImageButton startButton;

    @BindView(R.id.stop_button)
    ImageButton stopButton;

    @BindView(R.id.reset_button)
    ImageButton resetButton;

    @BindView(R.id.add_button)
    ImageButton addView;

    private int matchId;

    private Format format;

    private int formatId;

    private int miliseconds;

    private boolean running;

    private boolean timerType;//true если отчёт обратный

    private Unbinder unbinder;

    private MediaPlayer mediaPlayer;

    private DialogFragment dialogFragment;

    public MatchDetailFragment(int formatId, int matchId) {
        this.formatId = formatId;
        this.matchId = matchId;

    }


    public MatchDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_detail, container, false);
        runTimer(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.whistle);

        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
        format = qh.getFormatById(formatId);

        miliseconds = format.getPeriodMinutes() * 60 * 100;
        if (format.getKindOfSport().equals("football") || format.getKindOfSport().equals("basketball")) {
            timerType = true;
        } else {
            timerType = false;
        }

        Match match = qh.getMatchById(matchId);

        scores1.setText(Integer.toString(match.getScores1()));
        scores2.setText(Integer.toString(match.getScores2()));

        Team team1 = qh.getTeamById(match.getTeam1Id());
        Team team2 = qh.getTeamById(match.getTeam2Id());

        team1Name.setText(team1.getName());
        team2Name.setText(team2.getName());

        //TODO доделать хранение и извлечение логотипов!
        team1Logo.setImageResource(R.drawable.ic_menu_camera);
        team2Logo.setImageResource(R.drawable.ic_menu_gallery);

        return rootView;
    }

    @OnClick(R.id.start_button)
    public void onStartImageButtonClick() {

        mediaPlayer.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        mediaPlayer.stop();
        running = true;

    }

    @OnClick(R.id.add_button)
    public void onAddImageButtonClick() {
        miliseconds += 60 * 100;
    }

    @OnClick(R.id.stop_button)
    public void onStopImageButtonClick() {
        running = false;
    }

    @OnClick(R.id.reset_button)
    public void onResetImageButtonClick() {
        running = false;
        miliseconds = format.getPeriodMinutes() * 60 * 100;
    }

    private void runTimer(View view) {
        final TextView textView = (TextView) view.findViewById(R.id.timer);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = miliseconds / (60 * 60 * 100);
                int minutes = (miliseconds % (60 * 60 * 100)) / (60 * 100);
                int secs = (miliseconds % (60 * 100)) / 100;
                int mili = miliseconds % 100;

                String time = String.format("%d:%02d:%02d:%02d", hours, minutes, secs, mili);
                textView.setText(time);
                if (running) {
                    if (timerType) {
                        miliseconds--;
                    } else {
                        miliseconds++;
                    }

                }


                handler.postDelayed(this, 10);
            }
        });


    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();

    }
}
