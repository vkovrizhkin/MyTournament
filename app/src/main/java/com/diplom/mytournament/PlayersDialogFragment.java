package com.diplom.mytournament;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.diplom.mytournament.fragments.details_redact.MatchDetailFragment;
import com.diplom.mytournament.models.Player;

import java.util.List;

/**
 * Created by Kovrizhkin V.A. on 14.05.2017.
 */

public class PlayersDialogFragment extends DialogFragment {

    public interface PlayersInterface {
        void dialogOK(Player player, MatchDetailFragment matchDetailFragment);
    }


    private List<Player> playerList;

    private Player currentPlayer;

    private PlayersInterface dialogInterface;

    private MatchDetailFragment matchDetailFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        try {
            dialogInterface = (PlayersInterface) activity;
        } catch (ClassCastException c) {

        }
    }

    public PlayersDialogFragment(List<Player> playerList, MatchDetailFragment matchDetailFragment) {
        this.playerList = playerList;
        this.matchDetailFragment = matchDetailFragment;
        int i = 0;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] players = new String[playerList.size()];

        for (int i = 0; i < playerList.size(); i++) {
            players[i] = playerList.get(i).getFio();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите имя игрока")
                // добавляем переключатели
                .setSingleChoiceItems(players, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                currentPlayer = playerList.get(item);
                                // dialogInterface.dialogOK(currentPlayer);
/*                                Toast.makeText(
                                        getActivity(),
                                        "Любимое имя игрока: "
                                                + players[item], Toast.LENGTH_SHORT).show();*/
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        dialogInterface.dialogOK(currentPlayer, matchDetailFragment);
                        //matchDetailFragment.setCurrentPlayer(currentPlayer);



                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
