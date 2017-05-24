package com.diplom.mytournament.fragments.details_redact;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.diplom.mytournament.MyTournamentDatabaseHelper;
import com.diplom.mytournament.MyTournamentQueryHelper;
import com.diplom.mytournament.R;
import com.diplom.mytournament.models.Format;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCompetitionFragment extends Fragment {

    private String[] sports = new String[]{"Футбол", "Баскетбол", "Воллейбол"};

    private List<Format> formatList = new ArrayList<>();

    public static MyTournamentDatabaseHelper dbHelper;

    @BindView(R.id.competition_title_edit)
    EditText title;

    @BindView(R.id.kind_of_sport_spinner)
    Spinner sport;

    @BindView(R.id.format_spinner)
    Spinner format;

    @BindView(R.id.competition_date)
    DatePicker date;

    @BindView(R.id.submit_button)
    Button submit;

    @BindView(R.id.comp_info_edit)
    EditText info;


    public AddCompetitionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_add_competition, container, false);
        ButterKnife.bind(this, rootView);
        dbHelper =  new MyTournamentDatabaseHelper(getContext());
        final MyTournamentQueryHelper qh = new MyTournamentQueryHelper(getContext());
        ArrayAdapter<String> aAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout
                .simple_spinner_item, sports);
        aAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sport.setAdapter(aAdapter);
        sport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kindOfSport= "";
                switch (position) {
                    case 0:
                        kindOfSport = "football";
                        break;
                    case 2:
                        kindOfSport = "volleyball";
                        break;
                    case 1:
                        kindOfSport = "basketball";
                        break;
                    default:
                }

                formatList = qh.getFormatsBySport(kindOfSport);
                ArrayAdapter<Format> fAdapter = new ArrayAdapter<Format>(parent.getContext(), android.R.layout
                        .simple_spinner_item,formatList);
                fAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                format.setAdapter(fAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Format f = (Format)format.getSelectedItem();
                String dateString = Integer.toString(date.getDayOfMonth())+":"+Integer.toString(date.getMonth());

                dbHelper.insertCompetition(db, title.getText().toString(), "league", f.getId() , dateString,
                        info.getText().toString(), R.drawable.ic_menu_camera );

                Snackbar.make(rootView, "Соревнование создано!", Snackbar.LENGTH_LONG).show();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

}
