package com.diplom.mytournament.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.R;
import com.diplom.mytournament.activities.CompetitionActivity;
import com.diplom.mytournament.fragments.drawer.CompetitionsFragment;
import com.diplom.mytournament.models.Competition;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 27.04.2017.
 */

public class CompetitionsReсViewAdapter extends RecyclerView.Adapter<CompetitionsReсViewAdapter.ViewHolder> {

    private CompetitionsFragment competitionsFragment;

    private List<Competition> competitionList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.competition_title)
        TextView title;

        @BindView(R.id.logo_image)
        ImageView logo;

        @BindView(R.id.competition_content_layout)
        View container;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

    public CompetitionsReсViewAdapter(CompetitionsFragment competitionsFragment, List<Competition> competitionList) {
        this.competitionsFragment = competitionsFragment;
        this.competitionList = competitionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //создание нового представления
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.competition_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Competition competition = competitionList.get(position);

        holder.title.setText(competition.getName());
        holder.logo.setImageResource((int) competition.getLogoIdResource());


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.container.getContext(), CompetitionActivity.class);
                int id = competition.getId();
                intent.putExtra("competitionId", id);
                competitionsFragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return competitionList.size();
    }

}
