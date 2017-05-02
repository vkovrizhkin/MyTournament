package com.diplom.mytournament;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Вова on 09.04.2017.
 */

public class MyTournamentDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "myleague"; //имя базы данных
    private static final int DB_VERSION = 1; //версия базы данных

    public MyTournamentDatabaseHelper(Context context){

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        createMyDatabase(db);
        testInit(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    // метод создания локальной базы данных
    public void createMyDatabase(SQLiteDatabase db){

        //созднаие таблицы "Соревнование". Основные сведения о проводимом соревновании
        db.execSQL("CREATE TABLE COMPETITIONS("+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT,"+"TYPE TEXT,"+"KIND_OF_SPORT TEXT,"+
                "DATE TEXT,"+"INFO TEXT,"+"LOGO_RESOURCE_id INTEGER);");

        //создание сводной турнирной таблицы
        db.execSQL("CREATE TABLE STANDINGS("+"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEAM_id INTEGER,"+"COMPETITION_id INTEGER," + "S_GROUP TEXT,"+"PLAYED INTEGER," +
                "POINTS INTEGER,"+ "WON INTEGER, LOST INTEGER, DRAWN INTEGER, " +
                "GOALS_SCORED INTEGER, GOALS_AGAINST INTEGER);");
        //созднаие таблицы с командами
        db.execSQL("CREATE TABLE TEAMS("+"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT,"+"KIND_OF_SPORT TEXT,"+"LOGO_RESOURCE_id INTEGER,"+"INFO TEXT);");

        //создание таблицы с игроками
        db.execSQL("CREATE TABLE PLAYERS("+"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FIO TEXT,"+"TEAM_id INTEGER,"+"INFO TEXT);");

        //создание таблицы матчей
        db.execSQL("CREATE TABLE MATCHES("+"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "COMPETITION_id INTEGER,"+"DATE_TIME TEXT,"+"TEAM_H_id INTEGER,"+
                "TEAM_A_id INTEGER,"+"PLACE TEXT,"+"STAGE TEXT,"+"PLAYED INTEGER);");

        //создание таблицы с событиями
        db.execSQL("CREATE TABLE EVENTS("+"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MATCH_id INTEGER,"+"PLAYER_id INTEGER,"+"MINUTE INTEGER,"+"TYPE TEXT);");

    }

    //добавление соревнования
    public static void insertCompetition(SQLiteDatabase db,String name, String type, String sport,
                                         String date, String info, int resourceId){
        ContentValues competitionValues = new ContentValues();

        competitionValues.put("NAME", name);
        competitionValues.put("TYPE", type);
        competitionValues.put("KIND_OF_SPORT", sport);
        competitionValues.put("DATE", date);
        competitionValues.put("INFO", info);
        competitionValues.put("LOGO_RESOURCE_id", resourceId);

        db.insert("COMPETITIONS", null, competitionValues);

    }

    //добавление команды
    public static void insertTeam(SQLiteDatabase db, String name, String kindOfSport,
                                  int resourceId, String info){
        ContentValues teamValues = new ContentValues();

        teamValues.put("NAME", name);
        teamValues.put("KIND_OF_SPORT", kindOfSport);;
        teamValues.put("LOGO_RESOURCE_id", resourceId);
        teamValues.put("INFO", info);

        db.insert("TEAMS", null, teamValues);

    }

    //добавление матча
    public static void insertMatch(SQLiteDatabase db, int competitionId, String dateTime,
                                   String place, String stage, int teamHId, int teamAId){
        ContentValues matchValues = new ContentValues();

        matchValues.put("COMPETITION_id", competitionId);
        matchValues.put("DATE_TIME", dateTime);
        matchValues.put("PLACE", place);
        matchValues.put("STAGE", stage);
        matchValues.put("TEAM_H_id", teamHId);
        matchValues.put("TEAM_A_id", teamAId);

        db.insert("MATCHES", null, matchValues);
    }

    //добавление игрок
    public static void insertPlayer(SQLiteDatabase db, String fio, int teamId, String info){
        ContentValues playerValues = new ContentValues();

        playerValues.put("FIO", fio);
        playerValues.put("TEAM_id", teamId);
        playerValues.put("INFO", info);

        db.insert("PLAYERS", null, playerValues);

    }

    //добавление события
    public static void insertEvent(SQLiteDatabase db, int matchId, int playerId, int minute,
                                   String type){
        ContentValues eventValues = new ContentValues();

        eventValues.put("MATCH_id", matchId);
        eventValues.put("PLAYER_id", playerId);
        eventValues.put("MINUTE", minute);
        eventValues.put("TYPE", type);

        db.insert("EVENTS", null, eventValues);
    }

    public static void insertStanding(SQLiteDatabase db, int team_id, int compId){
        ContentValues standingValues = new ContentValues();


        standingValues.put("TEAM_id", team_id);
        standingValues.put("COMPETITION_id", compId);

        db.insert("STANDINGS", null, standingValues);


    }

    private static void testInit(SQLiteDatabase db) {
        // создаём два соревнования
        insertCompetition(db, "Лига Воронежа", "league", "football", "сегодня", "Все скидываем по косарю",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Кубок Липецка", "cup", "football", "завтра", "предварительная регистрация",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Лига Чемпионов", "league", "football", "сегодня", "Все скидываем по косарю",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Кубок Мира", "cup", "football", "завтра", "предварительная регистрация",
                R.drawable.ic_menu_camera);

        insertCompetition(db, "Лига Москвы", "league", "football", "сегодня", "Все скидываем по косарю",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Кубок Питера", "cup", "football", "завтра", "предварительная регистрация",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Лига Китая", "league", "football", "сегодня", "Все скидываем по косарю",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Кубок Африки", "cup", "football", "завтра", "предварительная регистрация",
                R.drawable.ic_menu_camera);

        for (int i = 0; i<15; i++){
            insertTeam(db, "Team"+ Integer.toString(i), "football", 0, "тестовая команда" );
            insertStanding(db, i , 1);

        }
        insertStanding(db, 2 , 2);
        insertStanding(db, 8 , 2);
        insertStanding(db, 9 , 2);

        insertMatch(db, 1, "12.12.12 12:00", "энергия", "1 тур", 1, 4);
        insertMatch(db, 1, "12.12.12 12:00", "стадион центральный", "1 тур", 2, 5);
        insertMatch(db, 1, "12.12.12 12:00", "энергия", "1 тур", 1, 4);
        insertMatch(db, 2, "12.12.12 12:00", "энергия", "1 тур", 1, 4);
        insertMatch(db, 3, "12.12.12 12:00", "энергия", "1 тур", 1, 4);
        insertMatch(db, 2, "12.12.12 12:00", "энергия", "1 тур", 1, 4);
        insertMatch(db, 3, "12.12.12 12:00", "энергия", "1 тур", 1, 4);

    }

}
