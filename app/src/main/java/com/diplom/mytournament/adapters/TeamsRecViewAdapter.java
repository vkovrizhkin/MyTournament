package com.diplom.mytournament.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 28.04.2017.
 */

public class TeamsRecViewAdapter extends RecyclerView.Adapter<TeamsRecViewAdapter.ViewHolder> {


    private List<Team> teamList;

    public TeamsRecViewAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.team_content_layout)
        View container;

        @BindView(R.id.team_name)
        TextView teamName;

        @BindView(R.id.team_logo)
        ImageView teamLogo;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Team team = teamList.get(position);

        holder.teamName.setText(team.getName());
        holder.teamLogo.setImageResource(R.drawable.ic_menu_camera);

    }


    @Override
    public int getItemCount() {
        return teamList.size();
    }
}
