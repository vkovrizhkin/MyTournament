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

    public MyTournamentDatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createMyDatabase(db);
        testInit(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // метод создания локальной базы данных
    public void createMyDatabase(SQLiteDatabase db) {

        //созднаие таблицы "Соревнование". Основные сведения о проводимом соревновании
        db.execSQL("CREATE TABLE COMPETITIONS(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," + "TYPE TEXT," + "FORMAT INTEGER," +
                "DATE TEXT," + "INFO TEXT," + "LOGO_RESOURCE_id TEXT);");

        //создание сводной турнирной таблицы
        db.execSQL("CREATE TABLE STANDINGS(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEAM_id INTEGER," + "COMPETITION_id INTEGER," + "S_GROUP TEXT," + "PLAYED INTEGER," +
                "POINTS INTEGER," + "WON INTEGER, LOST INTEGER, DRAWN INTEGER, " +
                "GOALS_SCORED INTEGER, GOALS_AGAINST INTEGER);");
        //созднаие таблицы с командами
        db.execSQL("CREATE TABLE TEAMS(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," + "KIND_OF_SPORT TEXT," + "LOGO_RESOURCE_id INTEGER," + "INFO TEXT);");

        //создание таблицы с игроками
        db.execSQL("CREATE TABLE PLAYERS(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FIO TEXT," + "TEAM_id INTEGER," + "INFO TEXT," +
                "NUMBER INTEGER, DATE_BORN INTEGER);");

        //создание таблицы матчей
        db.execSQL("CREATE TABLE MATCHES(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "COMPETITION_id INTEGER," + "DATE_TIME TEXT," + "TEAM_H_id INTEGER," +
                "TEAM_A_id INTEGER," + "SCORES1 INTEGER, " +
                "SCORES2 INTEGER," + "PLACE TEXT," + "STAGE TEXT," + "PLAYED INTEGER);");

        //создание таблицы с событиями
        db.execSQL("CREATE TABLE EVENTS(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MATCH_id INTEGER," + "PLAYER_id INTEGER," + "MINUTE INTEGER," + "TYPE TEXT," +
                "TEAM_SIDE TEXT);");

        db.execSQL("CREATE TABLE FORMATS(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, TEAM_PLAYERS INTEGER, " +
                "PERIOD_MINUTES INTEGER, PERIODS_NUM INTEGER, SCORES_IN_PERIOD INTEGER, " +
                "KIND_OF_SPORT TEXT);");

    }

    //добавление соревнования
    public  void insertCompetition(SQLiteDatabase db, String name, String type, int format,
                                         String date, String info, String logoUri) {
        ContentValues competitionValues = new ContentValues();

        competitionValues.put("NAME", name);
        competitionValues.put("TYPE", type);
        competitionValues.put("FORMAT", format);
        competitionValues.put("DATE", date);
        competitionValues.put("INFO", info);
        competitionValues.put("LOGO_RESOURCE_id", logoUri);

        db.insert("COMPETITIONS", null, competitionValues);

    }

    //добавление команды
    public static void insertTeam(SQLiteDatabase db, String name, String kindOfSport,
                                  String resourceId, String info) {
        ContentValues teamValues = new ContentValues();

        teamValues.put("NAME", name);
        teamValues.put("KIND_OF_SPORT", kindOfSport);
        ;
        teamValues.put("LOGO_RESOURCE_id", resourceId);
        teamValues.put("INFO", info);

        db.insert("TEAMS", null, teamValues);

    }

    //добавление матча
    public static void insertMatch(SQLiteDatabase db, long competitionId, String dateTime,
                                   String place, String stage, int teamHId, int teamAId, int played) {
        ContentValues matchValues = new ContentValues();

        matchValues.put("COMPETITION_id", competitionId);
        matchValues.put("DATE_TIME", dateTime);
        matchValues.put("PLACE", place);
        matchValues.put("STAGE", stage);
        matchValues.put("TEAM_H_id", teamHId);
        matchValues.put("TEAM_A_id", teamAId);
        matchValues.put("PLAYED", played);
        matchValues.put("SCORES1", 0);
        matchValues.put("SCORES2", 0);

        db.insert("MATCHES", null, matchValues);
    }

    //добавление игрок
    public static void insertPlayer(SQLiteDatabase db, String fio, int teamId, String info, int number) {
        ContentValues playerValues = new ContentValues();

        playerValues.put("FIO", fio);
        playerValues.put("TEAM_id", teamId);
        playerValues.put("INFO", info);
        playerValues.put("NUMBER", number);

        db.insert("PLAYERS", null, playerValues);

    }

    //добавление события
    public static void insertEvent(SQLiteDatabase db, int matchId, int playerId, int minute,
                                   String type) {
        ContentValues eventValues = new ContentValues();

        eventValues.put("MATCH_id", matchId);
        eventValues.put("PLAYER_id", playerId);
        eventValues.put("MINUTE", minute);
        eventValues.put("TYPE", type);

        db.insert("EVENTS", null, eventValues);
    }

    public static void insertStanding(SQLiteDatabase db, int team_id, long compId) {
        ContentValues standingValues = new ContentValues();

        standingValues.put("TEAM_id", team_id);
        standingValues.put("COMPETITION_id", compId);


        db.insert("STANDINGS", null, standingValues);


    }

    public static void insertFormat(SQLiteDatabase db, String name, int teamPlayers, int periodMinutes,
                                    int periodsNum, int scoresInPeriod, String kindOfSport) {
        ContentValues formatValues = new ContentValues();

        formatValues.put("NAME", name);
        formatValues.put("TEAM_PLAYERS", teamPlayers);
        formatValues.put("PERIOD_MINUTES", periodMinutes);
        formatValues.put("PERIODS_NUM", periodsNum);
        formatValues.put("SCORES_IN_PERIOD", scoresInPeriod);
        formatValues.put("KIND_OF_SPORT", kindOfSport);

        db.insert("FORMATS", null, formatValues);

    }


    private void testInit(SQLiteDatabase db) {

        insertFormat(db, "минифутбол", 5, 25, 2, 0, "football");
        insertFormat(db, "мидифутбол", 8, 30, 2, 0, "football");
        insertFormat(db, "футбол", 11, 45, 2, 0, "football");
        insertFormat(db, "воллейбол", 6, 0, 3, 25, "volleyball");
        insertFormat(db, "пляж. воллейбол", 2, 0, 3, 15, "volleyball");
        insertFormat(db, "баскетбол", 5, 15, 4, 0, "basketball");
        insertFormat(db, "стритбол", 3, 10, 4, 0, "basketball");

/*        // создаём два соревнования
        insertCompetition(db, "Лига Воронежа", "league", 1, "сегодня", "Все скидываем по косарю",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Кубок Липецка", "cup", 1, "завтра", "предварительная регистрация",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Лига Чемпионов", "league", 1, "сегодня", "Все скидываем по косарю",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Кубок Мира", "cup", 2, "завтра", "предварительная регистрация",
                R.drawable.ic_menu_camera);

        insertCompetition(db, "Лига Москвы", "league", 2, "сегодня", "Все скидываем по косарю",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Кубок Питера", "cup", 2, "завтра", "предварительная регистрация",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Лига Китая", "league", 6, "сегодня", "Все скидываем по косарю",
                R.drawable.ic_menu_camera);
        insertCompetition(db, "Кубок Африки", "cup", 4, "завтра", "предварительная регистрация",
                R.drawable.ic_menu_camera);
        for (int i = 1; i < 6; ++i) {


            insertPlayer(db, "Бубенцов", i, "умничка, по кайфу, молодец", 2);
            insertPlayer(db, "Петров", i, "умничка, по кайфу, молодец", 1);
            insertPlayer(db, "Иванов", i, "умничка, по кайфу, молодец", 3);
            insertPlayer(db, "Кузнецов", i, "умничка, по кайфу, молодец", 4);
            insertPlayer(db, "Джибриль Сиссе", i, "умничка, по кайфу, молодец",5);
        }

        for (int i = 0; i < 15; i++) {
            insertTeam(db, "Футбольная команда " + Integer.toString(i), "football", 0, "тестовая команда");
            insertTeam(db, "баскетбольная команда " + Integer.toString(i), "basketball", 0, "тестовая команда");
            insertTeam(db, "волейбольная команда " + Integer.toString(i), "volleyball", 0, "тестовая команда");
            if(i>0 && i<14){
                insertStanding(db, i, 1);
            }


        }

        insertMatch(db, 1, "12.12.12 12:00", "энергия", "1 тур", 1, 4, 0);
        insertMatch(db, 1, "12.12.12 12:00", "энергия", "1 тур", 2, 1, 1);
        insertMatch(db, 1, "12.12.12 12:00", "стадион центральный", "1 тур", 2, 5, 1);
        insertMatch(db, 1, "12.12.12 12:00", "энергия", "1 тур", 1, 4, 0);
        insertMatch(db, 2, "12.12.12 12:00", "энергия", "1 тур", 1, 4, 0);
        insertMatch(db, 3, "12.12.12 12:00", "энергия", "1 тур", 1, 4, 1);
        insertMatch(db, 2, "12.12.12 12:00", "энергия", "1 тур", 1, 4, 0);
        insertMatch(db, 3, "12.12.12 12:00", "энергия", "1 тур", 1, 4, 1);
        insertMatch(db, 7, "12.12.12 12:00", "энергия", "1 тур", 1, 4, 0);
        insertMatch(db, 7, "12.12.12 12:00", "энергия", "1 тур", 1, 4, 1);*/

    }

}
