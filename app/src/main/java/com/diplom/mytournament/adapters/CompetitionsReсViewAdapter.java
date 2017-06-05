package com.diplom.mytournament.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.activities.CompetitionActivity;
import com.diplom.mytournament.activities.MainActivity;
import com.diplom.mytournament.fragments.drawer.CompetitionsFragment;
import com.diplom.mytournament.models.Competition;
import com.diplom.mytournament.models.Format;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 27.04.2017.
 */

public class CompetitionsReсViewAdapter extends RecyclerView.Adapter<CompetitionsReсViewAdapter.ViewHolder> {

    private CompetitionsFragment competitionsFragment;

    private List<Competition> competitionList;

    private MainActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.competition_title)
        TextView title;

        @BindView(R.id.logo_image)
        ImageView logo;

        @BindView(R.id.competition_content_layout)
        View container;

        @BindView(R.id.format_title)
        TextView formatTitle;

        @BindView(R.id.progress)
        ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

    public CompetitionsReсViewAdapter(CompetitionsFragment competitionsFragment, List<Competition> competitionList, MainActivity activity) {
        this.competitionsFragment = competitionsFragment;
        this.competitionList = competitionList;
        this.activity = activity;
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
        MyTournamentQueryHelper qh = new MyTournamentQueryHelper(holder.container.getContext());

        Format format = qh.getFormatById(competition.getFormat());
        if (competition.getLogoIdResource() != null) {
            Bitmap img = null;
            Uri uri = Uri.parse(competition.getLogoIdResource());

            try {
                img = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                holder.progressBar.setVisibility(View.INVISIBLE);
            } catch (FileNotFoundException e) {
                // e.printStackTrace();
                Glide.with(holder.container.getContext()).load(uri).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .listener(new RequestListener<Uri, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                holder.progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                                holder.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(holder.logo);
/*                Picasso.with(holder.container.getContext()).load(uri).networkPolicy(NetworkPolicy.OFFLINE).into(holder.logo);*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.logo.setImageBitmap(img);
        } else {
            holder.logo.setImageResource(R.drawable.ic_menu_camera);
        }
        holder.title.setText(competition.getName());
        holder.formatTitle.setText(format.getName());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.container.getContext(), CompetitionActivity.class);
                long id = competition.getId();
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
