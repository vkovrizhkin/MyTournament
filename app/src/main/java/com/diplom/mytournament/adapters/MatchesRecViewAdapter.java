package com.diplom.mytournament.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.fragments.details_redact.MatchDetailFragment;
import com.diplom.mytournament.models.Match;
import com.diplom.mytournament.models.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 28.04.2017.
 */

public class MatchesRecViewAdapter extends RecyclerView.Adapter<MatchesRecViewAdapter.ViewHolder> {

    private List<Match> matchList;

    private int formatId;

    private static MyTournamentQueryHelper qh ;


    private FragmentManager fragmentManager;

    public MatchesRecViewAdapter(List<Match> matchList, FragmentManager fragmentManager, int formatId) {
        this.matchList = matchList;
        this.fragmentManager = fragmentManager;
        this.formatId = formatId;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.match_content_layout)
        View container;

        @BindView(R.id.match_stage)
        TextView stage;

        @BindView(R.id.match_date)
        TextView date;

        @BindView(R.id.team1_name)
        TextView team1Name;

        @BindView(R.id.team2_name)
        TextView team2Name;

        @BindView(R.id.team1_logo)
        ImageView team1Logo;

        @BindView(R.id.team2_logo)
        ImageView team2Logo;

        @BindView(R.id.scores1)
        TextView scores1;

        @BindView(R.id.scores2)
        TextView scores2;



        public ViewHolder(View view) {
            super(view);
            qh = new MyTournamentQueryHelper(view.getContext());
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MatchesRecViewAdapter.ViewHolder holder, int position) {

        final Match match = matchList.get(position);

        holder.stage.setText(match.getStage());
        holder.date.setText(match.getDate());
        if(match.getPlayed()==0){
            holder.scores1.setAlpha((float)0.3);
            holder.scores2.setAlpha((float)0.3);
        }
        holder.scores1.setText(Integer.toString(match.getScores1()));
        holder.scores2.setText(Integer.toString(match.getScores2()));

        Team team1 = qh.getTeamById(match.getTeam1Id());
        Team team2 = qh.getTeamById(match.getTeam2Id());

        holder.team1Name.setText(team1.getName());
        holder.team2Name.setText(team2.getName());

        //TODO доделать хранение и извлечение логотипов!
        holder.team1Logo.setImageResource(R.drawable.ic_menu_camera);
        holder.team2Logo.setImageResource(R.drawable.ic_menu_gallery);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchDetailFragment matchDetailFragment = new MatchDetailFragment(formatId, match.getId(), fragmentManager);
              fragmentManager.beginTransaction().replace(R.id.competition_frame_layout,
                        matchDetailFragment, "matchFragment").addToBackStack("matchFragment").commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }
}
