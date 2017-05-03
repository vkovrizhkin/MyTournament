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
        } finally {
            cursor.close();
            db.close();
        }
        return competitionList;

    }

    //получение всех событий матча
    public List<Event> getEventsByMatchId(int matchId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Event> eventList = new ArrayList<Event>();

        Cursor cursor = db.query("EVENTS", null,
                "MATCH_id = ?", new String[]{Integer.toString(matchId)}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);
                //int matchId = cursor.getInt(1);
                int playerId = cursor.getInt(2);
                int minute = cursor.getInt(3);
                String type = cursor.getString(4);

                eventList.add(new Event(id, matchId, playerId, type, minute));

            }
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                //int matchId = cursor.getInt(1);
                int playerId = cursor.getInt(2);
                int minute = cursor.getInt(3);
                String type = cursor.getString(4);

                eventList.add(new Event(id, matchId, playerId, type, minute));
            }
        } catch (SQLiteException e) {
            return null;
        } finally {
            cursor.close();
            db.close();
        }


        return eventList;

    }

    //получение всех событий соревнования
    public List<Event> getEventsByCompetitionId(int competitionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Event> eventList = new ArrayList<Event>();

        String sqlQuery = "SELECT * FROM EVENTS INNER JOIN MATCHES ON EVENTS.MATCH_id = MATCHES._id" +
                "WHERE MATCHES.COMPETITION_id = ?";
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{Integer.toString(competitionId)});

        try {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);
                int matchId = cursor.getInt(1);
                int playerId = cursor.getInt(2);
                int minute = cursor.getInt(3);
                String type = cursor.getString(4);

                eventList.add(new Event(id, matchId, playerId, type, minute));

            }
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                int matchId = cursor.getInt(1);
                int playerId = cursor.getInt(2);
                int minute = cursor.getInt(3);
                String type = cursor.getString(4);

                eventList.add(new Event(id, matchId, playerId, type, minute));
            }
        } catch (SQLiteException e) {
            return null;
        } finally {
            cursor.close();
            db.close();
        }

        return eventList;

    }

    //получение всех матчей расписания
    public List<Match> getMatchesByCompetitionId(int competitionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Match> matchList = new ArrayList<Match>();

        //  Cursor cursor = db.query("MATCHES", null, "COMPETITION_id", new String[]{Integer.toString(competitionId)}, null, null, null);
        Cursor cursor = db.query("MATCHES", null, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                //получение данных соревнования из курсора
                int id = cursor.getInt(0);
                //int competitionId = cursor.getInt(1);
                String dateTime = cursor.getString(2);
                int team1Id = cursor.getInt(3);
                int team2Id = cursor.getInt(4);
                int scores1 = cursor.getInt(5);
                int scores2 = cursor.getInt(6);
                String place = cursor.getString(7);
                String stage = cursor.getString(8);
                int played = cursor.getInt(9);

                matchList.add(new Match(id, competitionId, dateTime, stage, team1Id, team2Id, place,
                        played, scores1, scores2));


                //return competition;
            }
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                //int competitionId = cursor.getInt(1);
                String dateTime = cursor.getString(2);
                int team1Id = cursor.getInt(3);
                int team2Id = cursor.getInt(4);
                int scores1 = cursor.getInt(5);
                int scores2 = cursor.getInt(6);
                String place = cursor.getString(7);
                String stage = cursor.getString(8);
                int played = cursor.getInt(9);

                matchList.add(new Match(id, competitionId, dateTime, stage, team1Id, team2Id, place,
                        played, scores1, scores2));


            }


        } catch (SQLiteException e) {
            return null;
        }finally {
            cursor.close();
            db.close();
        }

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

        String SqlQuery = "SELECT * FROM MATCHES WHERE _id = ?";
        Cursor cursor = db.rawQuery(SqlQuery, new String[]{Integer.toString(matchId)});

        try {
            if (cursor.moveToFirst()) {
                //получение данных соревнования из курсора
               // int id = cursor.getInt(0);
                int competitionId = cursor.getInt(1);
                String dateTime = cursor.getString(2);
                int team1Id = cursor.getInt(3);
                int team2Id = cursor.getInt(4);
                int scores1 = cursor.getInt(5);
                int scores2 = cursor.getInt(6);
                String place = cursor.getString(7);
                String stage = cursor.getString(8);
                int played = cursor.getInt(9);
                return new Match(matchId, competitionId, dateTime, stage, team1Id, team2Id, place,
                        played, scores1, scores2);
            } else return null;

        } catch (SQLiteException e) {
            return null;
        } finally {
            cursor.close();
            db.close();
        }
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

        String SqlQuery = "SELECT * FROM TEAMS WHERE _id = ?";

        Cursor cursor = db.rawQuery(SqlQuery, new String[]{Integer.toString(teamId)});

        try {
            if (cursor.moveToFirst()) {
                //получение данных соревнования из курсора
                String name = cursor.getString(1);
                String sport = cursor.getString(2);
                long logo = (long) cursor.getInt(3);
                String info = cursor.getString(4);
                team = new Team(teamId, name, logo, sport, info);

                return team;
            } else return null;

        } catch (SQLiteException e) {
            return null;
        } finally {
            cursor.close();
            db.close();
        }


    }

    //получение всех команд соревнования
    public List<Team> getTeamsByCompetitionId(int competitionId) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Team> teamList = new ArrayList<>();

        String SqlQuery = "SELECT * FROM TEAMS INNER JOIN STANDINGS ON TEAMS._id = STANDINGS.TEAM_id " +
                "WHERE STANDINGS.COMPETITION_id = ?";


        Cursor cursor = db.rawQuery(SqlQuery, new String[]{Integer.toString(competitionId)});

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
        }finally {
            cursor.close();
            db.close();
        }
        return teamList;


    }


}
