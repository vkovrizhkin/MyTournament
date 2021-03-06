package com.diplom.mytournament.fragments.details_redact;


import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
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
import com.diplom.mytournament.fragments.PlayersDialogFragment;
import com.diplom.mytournament.R;
import com.diplom.mytournament.adapters.EventsRecViewAdapter;
import com.diplom.mytournament.models.Event;
import com.diplom.mytournament.models.Format;
import com.diplom.mytournament.models.Match;
import com.diplom.mytournament.models.Player;
import com.diplom.mytournament.models.Standing;
import com.diplom.mytournament.models.Team;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @BindView(R.id.current_time_text)
    TextView currentTimeText;

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

    @BindView(R.id.right_yc)
    ImageButton rightYC;

    @BindView(R.id.right_rc)
    ImageButton rightRC;

    @BindView(R.id.right_goal)
    ImageButton rightGoal;

    @BindView(R.id.events_rec_view)
    RecyclerView recyclerView;

    @BindView(R.id.finish_button)
    ImageButton finishButton;

    @BindView(R.id.next_time_button)
    Button nextTimeButton;

    private int matchId;

    private Match match;

    private Format format;

    private int formatId;

    private int miliseconds;

    private int eventMiliseconds = 0;

    private int currentTime = 1;

    private Map<String, String> eventsTagMap = new HashMap<String, String>();

    private boolean running;

    private boolean wasRunning;

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

    private View view;

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
        view = rootView;

        eventsTagMap.put("yellow_card", getString(R.string.yellow_card));
        eventsTagMap.put("red_card",getString(R.string.red_card) );
        eventsTagMap.put("goal", getString(R.string.goal));

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
        qh = new MyTournamentQueryHelper(getContext());
        match = qh.getMatchById(matchId);

        if (match.getPlayed() != 0) {
            disableButtons();
        }

        currentTimeText.setText(getString(R.string.time, currentTime));

        leftYC.setOnClickListener(eventClickListener);
        leftRC.setOnClickListener(eventClickListener);
        leftGoal.setOnClickListener(eventClickListener);
        rightYC.setOnClickListener(eventClickListener);
        rightRC.setOnClickListener(eventClickListener);
        rightGoal.setOnClickListener(eventClickListener);

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

        if (team1.getLogoResourceId() != null) {
            Bitmap img = null;
            Uri uri = Uri.parse(team1.getLogoResourceId());

            try {
                img = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            team1Logo.setImageBitmap(img);
        } else {
            team1Logo.setImageResource(R.drawable.ic_menu_camera);
        }
        if (team2.getLogoResourceId() != null) {
            Bitmap img = null;
            Uri uri = Uri.parse(team2.getLogoResourceId());

            try {
                img = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            team2Logo.setImageBitmap(img);
        } else {
            team2Logo.setImageResource(R.drawable.ic_menu_camera);
        }

        team1Name.setText(team1.getName());
        team2Name.setText(team2.getName());

        return rootView;
    }

    @OnClick(R.id.next_time_button)
    public void onNextTimeButtonClick() {
        currentTime++;
        running = false;
        wasRunning = false;
        miliseconds = format.getPeriodMinutes() * 60 * 100;
        currentTimeText.setText(getString(R.string.time, currentTime));
    }

    @OnClick(R.id.start_button)
    public void onStartImageButtonClick() {
        if (!wasRunning) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.whistle);
            mediaPlayer.start();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            mediaPlayer.stop();
            wasRunning = true;
        }

        running = true;

    }

    @OnClick(R.id.finish_button)
    public void onFinishButtonClick() {
        Standing standing1 = qh.getStandingsByTeamId(match.getTeam1Id(), match.getCompetitionId());
        Standing standing2 = qh.getStandingsByTeamId(match.getTeam2Id(), match.getCompetitionId());

        standing1.setGs(standing1.getGs() + match.getScores1());
        standing1.setGa(standing1.getGa() + match.getScores2());
        standing2.setGs(standing2.getGs() + match.getScores2());
        standing2.setGa(standing2.getGa() + match.getScores1());

        standing1.setMatchesPlayed(standing1.getMatchesPlayed() + 1);
        standing2.setMatchesPlayed(standing2.getMatchesPlayed() + 1);


        if (match.getScores1() == match.getScores2()) {
            standing1.setDrawn(standing1.getDrawn() + 1);
            standing1.setPoints(standing1.getPoints() + 1);
            standing2.setDrawn(standing2.getDrawn() + 1);
            standing2.setPoints(standing2.getPoints() + 1);
        } else if (match.getScores1() > match.getScores2()) {
            standing1.setWon(standing1.getWon() + 1);
            standing1.setPoints(standing1.getPoints() + 3);
            standing2.setLost(standing2.getLost() + 1);
        } else {
            standing2.setWon(standing2.getWon() + 1);
            standing2.setPoints(standing2.getPoints() + 3);
            standing1.setLost(standing1.getLost() + 1);
        }
        disableButtons();
        qh.updateStandings(standing1);
        qh.updateStandings(standing2);
        match.setPlayed(1);
        qh.upadateMatch(match);

        Snackbar snackbar = Snackbar.make(view, getString(R.string.match_over), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.share, new shareListener());
        snackbar.show();

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
        wasRunning = false;
        miliseconds = format.getPeriodMinutes() * 60 * 100;
    }

    private void runTimer(View view) {
        final TextView textView = (TextView) view.findViewById(R.id.timer);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = miliseconds / 360000;
                int minutes = (miliseconds % 360000) / (6000);
                int secs = (miliseconds % 6000) / 100;
                int mili = miliseconds % 100;

                String time = String.format("%d:%02d:%02d:%02d", hours, minutes, secs, mili);
                textView.setText(time);
                if (running) {
                    if (timerType) {
                        miliseconds--;
                    } else {
                        miliseconds++;
                    }
                    eventMiliseconds++;
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
        int eventMinute = ((eventMiliseconds % (60 * 60 * 100)) / (60 * 100)) * currentTime + 1;
        Event event = new Event(1, matchId, player.getId(), "yellow_card", eventMinute, 'l');
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
        scores1.setText(Integer.toString(match.getScores1()));
        scores2.setText(Integer.toString(match.getScores2()));

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
                    match.setScores1(match.getScores1() + 1);
                    type = "goal";
                    break;
                case R.id.right_yc:
                    playerList = qh.getPlayersByTeamId(match.getTeam2Id());
                    side = 'r';
                    type = "yellow_card";
                    break;
                case R.id.right_rc:
                    playerList = qh.getPlayersByTeamId(match.getTeam2Id());
                    side = 'r';
                    type = "red_card";
                    break;
                case R.id.right_goal:
                    playerList = qh.getPlayersByTeamId(match.getTeam2Id());
                    side = 'r';
                    type = "goal";
                    match.setScores2(match.getScores2() + 1);
                    break;

            }
            int index = fragmentManager.getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
            String tag = backEntry.getName();
            MatchDetailFragment fragment = (MatchDetailFragment) fragmentManager.findFragmentByTag(tag);
            myDialogFragment = new PlayersDialogFragment(playerList, fragment);
            myDialogFragment.show(fragmentManager, "dialog");
            //currentPlayer = myDialogFragment.getCurrentPlayer();
            int eventMinute = ((eventMiliseconds % (60 * 60 * 100)) / (60 * 100)) + (currentTime - 1) * format.getPeriodMinutes() + 1;
            currentEvent = new Event(1, matchId, 0, type, eventMinute, side);

        }
    };

    public void disableButtons() {
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
       // finishButton.setEnabled(false);
        nextTimeButton.setEnabled(false);
        leftYC.setEnabled(false);
        leftRC.setEnabled(false);
        leftGoal.setEnabled(false);
        rightRC.setEnabled(false);
        rightYC.setEnabled(false);
        rightGoal.setEnabled(false);

        startButton.setAlpha((float) 0.5);
        stopButton.setAlpha((float) 0.5);
        resetButton.setAlpha((float) 0.5);
        finishButton.setAlpha((float) 0.5);
        nextTimeButton.setAlpha((float) 0.5);
        leftYC.setAlpha((float) 0.5);
        leftRC.setAlpha((float) 0.5);
        leftGoal.setAlpha((float) 0.5);
        rightRC.setAlpha((float) 0.5);
        rightYC.setAlpha((float) 0.5);
        rightGoal.setAlpha((float) 0.5);
        timer.setAlpha((float) 0.5);
        currentTimeText.setAlpha((float) 0.5);


    }

    public class shareListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String forPost = "";
            forPost+="Матч завершён! \n"+team1Name.getText()+" "+scores1.getText()+" - "+scores2.getText()+ " "+team2Name.getText()+"\n";
            for(int i = 0; i<eventList.size(); i++)
            {

                String fio = qh.getPlayersById(eventList.get(i).getPlayerId()).getFio();
                String minute = eventList.get(i).getMinute()+"'";
                forPost+=minute+"  "+eventsTagMap.get(eventList.get(i).getType())+" " + fio + "\n";
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, forPost);


            // intent.putExtra(ReceiveMessegeActivity.EXTRA_MESSEGE, messegeText);
            startActivity(intent);
            // Code to undo the user's last action
        }
    }
}
