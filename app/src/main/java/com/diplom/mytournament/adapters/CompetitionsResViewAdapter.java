package com.diplom.mytournament.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Competition;

import java.util.List;

/**
 * Created by Kovrizhkin V.A. on 27.04.2017.
 */

public class CompetitionsResViewAdapter extends RecyclerView.Adapter<CompetitionsResViewAdapter.ViewHolder>{

private List<Competition> competitionList;

public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView logo;

    public ViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        logo = (ImageView) view.findViewById(R.id.logo_image);

    }

}

    public CompetitionsResViewAdapter(List<Competition> competitionList) {
        this.competitionList = competitionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //создание нового представления
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.competition_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Competition competition = competitionList.get(position);

        holder.title.setText(competition.getName());
        holder.logo.setImageResource((int) competition.getLogoIdResource());

    }

    @Override
    public int getItemCount() {

        return competitionList.size();
    }


}
