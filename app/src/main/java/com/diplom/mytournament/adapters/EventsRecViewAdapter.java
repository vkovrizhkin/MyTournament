package com.diplom.mytournament.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Kovrizhkin V.A. on 10.05.2017.
 */

public class EventsRecViewAdapter extends RecyclerView.Adapter<EventsRecViewAdapter.ViewHolder> {

    private List<Event> eventList;

    static private Map<String, Integer> eventsImagesMap = new HashMap<String, Integer>();


    public EventsRecViewAdapter(List<Event> eventList) {
        mapInit();
        this.eventList = eventList;
    }

    private static void mapInit() {
        eventsImagesMap.put("yellow_card", R.drawable.yellow_card);
        eventsImagesMap.put("red_card", R.drawable.red_card);
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
        if(event.getSide()=='l'){
            holder.leftFio.setText("Бубенцов");
            holder.leftLogo.setImageResource(eventsImagesMap.get(event.getType()));
            holder.min.setText(Integer.toString(event.getMinute()));
        }


    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
