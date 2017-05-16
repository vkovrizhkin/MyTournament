package com.diplom.mytournament.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Event;
import com.diplom.mytournament.models.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kovrizhkin V.A. on 10.05.2017.
 */

public class EventsRecViewAdapter extends RecyclerView.Adapter<EventsRecViewAdapter.ViewHolder> {

    private List<Event> eventList;

    static private Map<String, Integer> eventsImagesMap = new HashMap<String, Integer>();

    private Context context;
    MyTournamentQueryHelper qh;


    public EventsRecViewAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
        qh = new MyTournamentQueryHelper(context);
        mapInit();

    }

    private static void mapInit() {
        eventsImagesMap.put("yellow_card", R.drawable.yellow_card);
        eventsImagesMap.put("red_card", R.drawable.red_card);
        eventsImagesMap.put("goal", R.drawable.ic_goal);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_content_layout)
        View container;

        @BindView(R.id.event_min)
        TextView min;

        @BindView(R.id.event_left_fio)
        TextView leftFio;

        @BindView(R.id.event_right_fio)
        TextView rightFio;

        @BindView(R.id.event_left_logo)
        ImageView leftLogo;

        @BindView(R.id.event_right_logo)
        ImageView rightLogo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsRecViewAdapter.ViewHolder holder, int position) {
        Event event = eventList.get(position);
        Player player = qh.getPlayersById(event.getPlayerId());
        if(event.getSide()=='l'){
            holder.leftFio.setText(player.getFio());
            holder.leftLogo.setImageResource(eventsImagesMap.get(event.getType()));
            holder.min.setText(Integer.toString(event.getMinute())+"'");
        } else {
            holder.rightFio.setText(player.getFio());
            holder.rightLogo.setImageResource(eventsImagesMap.get(event.getType()));
            holder.min.setText(Integer.toString(event.getMinute())+"'");
        }


    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


}
