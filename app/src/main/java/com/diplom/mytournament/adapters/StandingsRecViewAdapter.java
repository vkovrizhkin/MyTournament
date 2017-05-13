package com.diplom.mytournament.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Event;
import com.diplom.mytournament.models.Standing;
import com.diplom.mytournament.models.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Kovrizhkin V.A. on 10.05.2017.
 */

public class StandingsRecViewAdapter extends RecyclerView.Adapter<StandingsRecViewAdapter.ViewHolder> {

    private List<Standing> standingList;

    MyTournamentQueryHelper qh;

    public StandingsRecViewAdapter(List<Standing> standingList, Context context) {
        this.standingList = standingList;
        qh = new MyTournamentQueryHelper(context);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.st_num)
        TextView num;

        @BindView(R.id.st_team_name)
        TextView teamName;

        @BindView(R.id.st_played)
        TextView played;

        @BindView(R.id.st_won)
        TextView won;

        @BindView(R.id.st_lost)
        TextView lost;

        @BindView(R.id.st_drawn)
        TextView drawn;

        @BindView(R.id.st_goals)
        TextView goals;

        @BindView(R.id.st_points)
        TextView points;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.standing_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StandingsRecViewAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            // Standing standing = standingList.get(position);
            holder.num.setText(" ");
            holder.teamName.setText("НАЗВАНИЕ");
            holder.played.setText("И");
            holder.won.setText("В");
            holder.lost.setText("П");
            holder.drawn.setText("Н");
            holder.goals.setText("Г");
            holder.points.setText("О");
        } else {
            Standing standing = standingList.get(position - 1);
            int id = standing.getTeamId();
            //String teamName = qh.getTeamById(standing.getTeamId()).getName();
            Team team = qh.getTeamById(id);
            String teamName = team.getName();

            holder.teamName.setSelected(true);

            holder.num.setText(Integer.toString(position));
            holder.teamName.setText(teamName);
            holder.played.setText(Integer.toString(standing.getMatchesPlayed()));
            holder.won.setText(Integer.toString(standing.getWon()));
            holder.lost.setText(Integer.toString(standing.getLost()));
            holder.drawn.setText(Integer.toString(standing.getMatchesPlayed() - standing.getLost() -
                    standing.getWon()));
            holder.goals.setText(Integer.toString(standing.getGs()) + "|" + Integer.toString(standing.getGa()));
            holder.points.setText(Integer.toString(standing.getPoints()));
        }


    }


    @Override
    public int getItemCount() {
        return standingList.size() + 1;
    }


}
