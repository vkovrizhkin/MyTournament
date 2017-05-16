package com.diplom.mytournament.fragments.details_redact;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.PlayersDialogFragment;
import com.diplom.mytournament.R;
import com.diplom.mytournament.adapters.EventsRecViewAdapter;
import com.diplom.mytournament.models.Event;
import com.diplom.mytournament.models.Format;
import com.diplom.mytournament.models.Match;
import com.diplom.mytournament.models.Player;
import com.diplom.mytournament.models.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchDetailFragment extends Fragment implements PlayersDialogFragment.PlayersInterface {

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

    @BindView(R.id.left_yc)
    ImageButton leftYC;

    @BindView(R.id.left_goal)
    ImageButton leftGoal;

    @BindView(R.id.left_rc)
    ImageButton leftRC;


    @BindView(R.id.events_rec_view)
    RecyclerView recyclerView;

    private int matchId;

    private Match match;

    private Format format;

    private int formatId;

    private int miliseconds;

    private boolean running;

    private boolean timerType;//true если отчёт обратный

    private Unbinder unbinder;

    private MediaPlayer mediaPlayer;

    private PlayersDialogFragment myDialogFragment;

    private Player currentPlayer;

    private List<Event> eventList = new ArrayList<>();

    private Event currentEvent;

    private EventsRecViewAdapter rAdapter;

    private FragmentManager fragmentManager;

    private MyTournamentQueryHelper qh;

    public MatchDetailFragment(int formatId, int matchId, FragmentManager fragmentManager) {
        this.formatId = formatId;
        this.matchId = matchId;
        this.fragmentManager = fragmentManager;
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
        team1Name.setSelected(true);
        team2Name.setSelected(true);
        rAdapter = new EventsRecViewAdapter(eventList, getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.whistle);
        qh = new MyTournamentQueryHelper(getContext());
        match = qh.getMatchById(matchId);

        leftYC.setOnClickListener(eventClickListener);
        leftRC.setOnClickListener(eventClickListener);
        leftGoal.setOnClickListener(eventClickListener);

/*        leftYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Player> playerList = qh.getPlayersByTeamId(match.getTeam1Id());
                // FragmentManager manager = getFragmentManager();
                int index = fragmentManager.getBackStackEntryCount() - 1;
                FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
                String tag = backEntry.getName();
                MatchDetailFragment fragment = (MatchDetailFragment) fragmentManager.findFragmentByTag(tag);
                myDialogFragment = new PlayersDialogFragment(playerList, fragment);
                myDialogFragment.show(fragmentManager, "dialog");
                // currentPlayer = myDialogFragment.getCurrentPlayer();
            }
        });*/


        format = qh.getFormatById(formatId);

        miliseconds = format.getPeriodMinutes() * 60 * 100;
        if (format.getKindOfSport().equals("football") || format.getKindOfSport().equals("basketball")) {
            timerType = true;
        } else {
            timerType = false;
        }


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

    @Override
    public void dialogOK(Player player, MatchDetailFragment matchDetailFragment) {
        Event event = new Event(1, matchId, player.getId(), "yellow_card", (miliseconds % (60 * 60 * 100)) / (60 * 100), 'l');
        eventList.add(event);
        rAdapter.notifyItemInserted(eventList.size() - 1);
        rAdapter.notifyDataSetChanged();

    }

    public void executeEventAdding(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        currentEvent.setPlayerId(currentPlayer.getId());
        eventList.add(currentEvent);
        rAdapter = new EventsRecViewAdapter(eventList, getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //  rAdapter.notifyItemInserted(eventList.size()-1);
        // rAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener eventClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
            List<Player> playerList = new ArrayList<>();

            char side = ' ';

            String type = "";

            switch (v.getId()) {
                case R.id.left_yc:
                    playerList = qh.getPlayersByTeamId(match.getTeam1Id());
                    side = 'l';
                    type = "yellow_card";
                    break;
                case R.id.left_rc:
                    playerList = qh.getPlayersByTeamId(match.getTeam1Id());
                    side = 'l';
                    type = "red_card";
                    break;
                case R.id.left_goal:
                    playerList = qh.getPlayersByTeamId(match.getTeam1Id());
                    side = 'l';
                    type = "goal";
                    break;

            }

/*            if (v.getId() == R.id.left_yc || v.getId() == R.id.left_rc) {


            } else {
                playerList = qh.getPlayersByTeamId(match.getTeam2Id());
                side = 'r';
            }*/
            int index = fragmentManager.getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
            String tag = backEntry.getName();
            MatchDetailFragment fragment = (MatchDetailFragment) fragmentManager.findFragmentByTag(tag);
            myDialogFragment = new PlayersDialogFragment(playerList, fragment);
            myDialogFragment.show(fragmentManager, "dialog");
            //currentPlayer = myDialogFragment.getCurrentPlayer();
           currentEvent = new Event(1, matchId, 0, type, (miliseconds % (60 * 60 * 100)) / (60 * 100), side);
           /*  eventList.add(event);
            rAdapter = new EventsRecViewAdapter(eventList, getContext());
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(rAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);*/
            // currentPlayer = myDialogFragment.getCurrentPlayer();

        }
    };
}
