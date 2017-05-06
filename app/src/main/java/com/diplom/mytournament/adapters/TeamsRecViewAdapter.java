package com.diplom.mytournament.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.R;
import com.diplom.mytournament.fragments.TeamFragment;
import com.diplom.mytournament.models.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 28.04.2017.
 */

public class TeamsRecViewAdapter extends RecyclerView.Adapter<TeamsRecViewAdapter.ViewHolder> {


    private List<Team> teamList;

    FragmentManager fragmentManager;

    public TeamsRecViewAdapter(List<Team> teamList, FragmentManager fragmentManager) {
        this.teamList = teamList;
        this.fragmentManager = fragmentManager;
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

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TeamFragment();
                fragmentManager.beginTransaction().replace(R.id.competition_frame_layout, fragment).addToBackStack(null).commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return teamList.size();
    }
}
