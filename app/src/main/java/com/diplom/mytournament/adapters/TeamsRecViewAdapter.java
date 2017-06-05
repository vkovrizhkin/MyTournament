package com.diplom.mytournament.adapters;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.R;
import com.diplom.mytournament.activities.CompetitionActivity;
import com.diplom.mytournament.fragments.details_redact.TeamFragment;
import com.diplom.mytournament.models.Team;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 28.04.2017.
 */

public class TeamsRecViewAdapter extends RecyclerView.Adapter<TeamsRecViewAdapter.ViewHolder> {


    private List<Team> teamList;

    private long competitionId;

    private CompetitionActivity activity;

    FragmentManager fragmentManager;

    public TeamsRecViewAdapter(List<Team> teamList, long competitionId, CompetitionActivity activity, FragmentManager fragmentManager) {
        this.teamList = teamList;
        this.competitionId = competitionId;
        this.activity = activity;
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

        if (team.getLogoResourceId()!=null){
            Bitmap img = null;
            Uri uri = Uri.parse(team.getLogoResourceId());

            try {
                img = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.teamLogo.setImageBitmap(img);
        } else {
            holder.teamLogo.setImageResource(R.drawable.ic_menu_camera);
        }

        holder.teamName.setText(team.getName());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TeamFragment(team.getId(), competitionId);
                fragmentManager.beginTransaction().replace(R.id.competition_frame_layout, fragment).addToBackStack(null).commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        if(teamList==null) {
            return 0;
        } else{
            return teamList.size();
        }

    }
}
