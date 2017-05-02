package com.diplom.mytournament.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
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

    private static MyTournamentQueryHelper qh ;

    public MatchesRecViewAdapter(List<Match> matchList) {
        this.matchList = matchList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.match_stage)
        TextView stage;

       // @BindView(R.id.match_date)
        TextView date;

       // @BindView(R.id.team1_name)
        TextView team1Name;

       // @BindView(R.id.team2_name)
        TextView team2Name;

       // @BindView(R.id.team1_logo)
        ImageView team1Logo;

        //@BindView(R.id.team2_logo)
        ImageView team2Logo;


        public ViewHolder(View view) {
            super(view);
            qh = new MyTournamentQueryHelper(view.getContext());
            stage = (TextView) view.findViewById(R.id.match_stage);
            date = (TextView)view.findViewById(R.id.match_date);
            team1Name = (TextView)view.findViewById(R.id.team1_name);
            team2Name = (TextView)view.findViewById(R.id.team2_name);
            team1Logo = (ImageView)view.findViewById(R.id.team1_logo);
            team2Logo = (ImageView)view.findViewById(R.id.team2_logo);


            //ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchesRecViewAdapter.ViewHolder holder, int position) {

        Match match = matchList.get(position);

        holder.stage.setText(match.getStage());
        holder.date.setText(match.getDate());

        Team team1 = qh.getTeamById(match.getTeam1Id());
        Team team2 = qh.getTeamById(match.getTeam2Id());

        holder.team1Name.setText(team1.getName());
        holder.team2Name.setText(team2.getName());

        //TODO доделать хранение и извлечение логотипов!
        holder.team1Logo.setImageResource(R.drawable.ic_menu_camera);
        holder.team2Logo.setImageResource(R.drawable.ic_menu_gallery);


    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }
}
