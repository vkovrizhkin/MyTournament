package com.diplom.mytournament;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.diplom.mytournament.models.Competition;
import com.diplom.mytournament.models.Event;
import com.diplom.mytournament.models.Match;
import com.diplom.mytournament.models.Player;
import com.diplom.mytournament.models.Standing;
import com.diplom.mytournament.models.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Вова on 10.04.2017.
 */

public class MyTournamentQueryHelper {

    public static MyTournamentDatabaseHelper dbHelper;

    public MyTournamentQueryHelper(Context context) {

        dbHelper = new MyTournamentDatabaseHelper(context);
    }

    //получение соревнования по идентификатору
    public Competition getCompetitionById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("COMPETITIONS", null,
                "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        Competition competition;

        try {
            if (cursor.moveToFirst()) {
                //получение данных соревнования из курсора
                String name = cursor.getString(0);
                String kindOfSport = cursor.getString(1);
                String info = cursor.getString(2);
                String date = cursor.getString(3);
                String type = cursor.getString(4);
                int resourceId = cursor.getInt(5);

                competition = new Competition(name, id, kindOfSport, info, date, type, resourceId);

                return competition;
            } else return null;

        } catch (SQLiteException e) {
            return null;
        } finally {
            cursor.close();
            db.close();
        }

    }

    //получение списка всех соревнований на устройстве
    public List<Competition> getAllCompetition() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Competition> competitionList = new ArrayList<Competition>();

        Cursor cursor = db.query("COMPETITIONS", new String[]{"_id", "NAME", "KIND_OF_SPORT",
                        "INFO", "DATE", "TYPE", "LOGO_RESOURCE_ID"},
                null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                //получение данных соревнования из курсора
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String kindOfSport = cursor.getString(2);
                String info = cursor.getString(3);
                String date = cursor.getString(4);
                String type = cursor.getString(5);
                int resourceId = cursor.getInt(6);

                Competition competition = new Competition(name, id, kindOfSport, info, date, type, resourceId);
                competitionList.add(competition);

                //return competition;
            }
            while (cursor.moveToNext()) {
                //получение данных соревнования из курсора
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String kindOfSport = cursor.getString(2);
                String info = cursor.getString(3);
                String date = cursor.getString(4);
                String type = cursor.getString(5);
                int resourceId = cursor.getInt(6);

                Competition competition = new Competition(name, id, kindOfSport, info, date, type, resourceId);
                competitionList.add(competition);

            }


        } catch (SQLiteException e) {
            return null;
        }
        return competitionList;

    }

    //получение всех событий матча
    public List<Event> getEventsByMatchId(int matchId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Event> eventList = new ArrayList<Event>();

        Cursor cursor = db.query("EVENTS", new String[]{"_id", "MATCH_id", "PLAYER_id", "TYPE", "MINUTE"},
                "MATCH_id = ?", new String[]{Integer.toString(matchId)}, null, null, null);

        return eventList;

    }

    //получение всех событий соревнования
    public List<Event> getEventsByCompetitionId(int competitionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Event> eventList = new ArrayList<Event>();

        String sqlQuery = "SELECT E._id AS _id , E.MATCH_id AS MATCH_id," +
                " E.PLAYER_id AS PLAYER_id, E.TYPE AS TYPE, E.MINUTE AS MINUTE" +
                "FROM EVENTS AS E INNER JOIN MATCHES AS M ON E.MATCH_id = M._id" +
                "WHERE M.COMPETITION_id = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{Integer.toString(competitionId)});

        return eventList;

    }

    //получение всех матчей расписания
    public List<Match> getMatchesByCompetitionId(int competitionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Match> matchList = new ArrayList<Match>();

        Cursor cursor = db.query("MATCHES", null, "COMPETITION_id", new String[]{Integer.toString(competitionId)}, null, null, null);

        return matchList;

    }

    //получение всех матчей команды
    public List<Match> getMatchesByTeamId(int teamId, int competitionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Match> matchList = new ArrayList<Match>();

        return matchList;

    }

    //получение матча по идентификатору
    public Match getMatchById(int matchId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Match match;

        return null;
    }

    //получение игрока по идентификатору
    public Player getPlayersById(int playerId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Player player;

        return null;

    }

    //получение игроков команды
    public List<Player> getPlayersByTeamId(int teamId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Player> playerList = new ArrayList<>();

        return playerList;


    }

    //получение турнирной таблицы для соревнования
    public List<Standing> getStandingsByCompetitionId(int competitionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Standing> standingList = new ArrayList<>();

        return standingList;

    }

    //получение турнирных данных для команды во всех соревнованиях
    public List<Standing> getStandingsByTeamId(int teamId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Standing> standingList = new ArrayList<>();

        return standingList;

    }

    //получение команды по идентификатору
    public Team getTeamById(int teamId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Team team;

        return null;

    }

    //получение всех команд соревнования
    public List<Team> getTeamsByCompetitionId(int competitionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Team> teamList = new ArrayList<>();
       /* String SqlQuery = "SELECT * FROM TEAMS INNER JOIN STANDINGS ON TEAMS._id = STANDINGS.TEAM_id" +
                " WHERE STANDINGS.COMPETITION_id = ?";*/

        String SqlQuery = "SELECT * FROM TEAMS INNER JOIN STANDINGS ON TEAMS._id = STANDINGS.TEAM_id " +
                "WHERE STANDINGS.COMPETITION_id = ?";



          Cursor cursor = db.rawQuery(SqlQuery, new String[]{Integer.toString(competitionId)});
          Cursor cursor2 = db.query("STANDINGS", null, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                //получение данных соревнования из курсора
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int resourceId = cursor.getInt(3);
                String kindOfSport = cursor.getString(2);
                String info = cursor.getString(4);

                Team team = new Team(id, name, resourceId, kindOfSport, info);
                teamList.add(team);

                //return competition;
            }
            while (cursor.moveToNext()) {
                //получение данных соревнования из курсора
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int resourceId = cursor.getInt(2);
                String kindOfSport = cursor.getString(3);
                String info = cursor.getString(4);

                Team team = new Team(id, name, resourceId, kindOfSport, info);
                teamList.add(team);

            }


        } catch (SQLiteException e) {
            return null;
        }
        return teamList;


    }


}
