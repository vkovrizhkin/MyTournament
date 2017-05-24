package com.diplom.mytournament.fragments.details_redact;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.diplom.mytournament.MyTournamentDatabaseHelper;
import com.diplom.mytournament.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPlayerFragment extends Fragment {

    private int teamId;

    private MyTournamentDatabaseHelper dbHelper;

   @BindView(R.id.player_fio_edit)
    EditText playerFio;

    @BindView(R.id.player_date_born)
    DatePicker dateBorn;

    @BindView(R.id.number_edit)
    EditText number;

    @BindView(R.id.player_info_edit)
    EditText playerInfo;

    @BindView(R.id.player_submit_button)
    Button submitButton;

    public AddPlayerFragment(int teamId) {
        this.teamId = teamId;
    }

    public AddPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_player, container, false);
        ButterKnife.bind(this, rootView);
        dbHelper = new MyTournamentDatabaseHelper(getContext());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                dbHelper.insertPlayer(db, playerFio.getText().toString(), teamId, playerInfo.getText().toString(), Integer.parseInt(number.getText().toString()));

                Snackbar.make(rootView, "Игрок Добавлен!", Snackbar.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

}
