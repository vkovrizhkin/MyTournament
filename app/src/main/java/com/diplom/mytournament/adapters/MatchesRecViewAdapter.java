package com.diplom.mytournament.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Match;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 28.04.2017.
 */

public class MatchesRecViewAdapter extends RecyclerView.Adapter<MatchesRecViewAdapter.ViewHolder> {

    private List<Match> matchList;

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


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchesRecViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
