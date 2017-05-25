package com.diplom.mytournament.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Player;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 28.04.2017.
 */

public class PlayersRecViewAdapter extends RecyclerView.Adapter<PlayersRecViewAdapter.ViewHolder> {

    private List<Player> playerList = new ArrayList<>();

    private int teamId;

    public PlayersRecViewAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.player_content_layout)
        View container;

        @BindView(R.id.player_fio)
        TextView fio;

        @BindView(R.id.player_matches)
        TextView matches;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayersRecViewAdapter.ViewHolder holder, int position) {

        Player player = playerList.get(position);

        holder.fio.setText(player.getFio());

        holder.matches.setText("лол, кек, чебурек");

    }

    @Override
    public int getItemCount() {
        if (playerList == null) {
            return 0;
        } else {
            return playerList.size();
        }

    }


}
