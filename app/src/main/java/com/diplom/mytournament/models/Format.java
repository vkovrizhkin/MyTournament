package com.diplom.mytournament.models;

/**
 * Created by Kovrizhkin V.A. on 04.05.2017.
 */

public class Format {

    private int id;

    private String name; //название формата

    private int teamPlayers; //количество игроков на поле от одной команды

    private int periodMinutes;//минут в одном тайме

    private int periodsNum; //количество таймов

    private int scoresInPeriod;//максимальное количество очков в партии

    private String kindOfSport;//вид спорта

    public Format(int id, String name, int teamPlayers, int periodMinutes, int periodsNum, int scoresInPeriod, String kindOfSport) {
        this.id = id;
        this.name = name;
        this.teamPlayers = teamPlayers;
        this.periodMinutes = periodMinutes;
        this.periodsNum = periodsNum;
        this.scoresInPeriod = scoresInPeriod;
        this.kindOfSport = kindOfSport;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeamPlayers() {
        return teamPlayers;
    }

    public void setTeamPlayers(int teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public int getPeriodMinutes() {
        return periodMinutes;
    }

    public void setPeriodMinutes(int periodMinutes) {
        this.periodMinutes = periodMinutes;
    }

    public int getPeriodsNum() {
        return periodsNum;
    }

    public void setPeriodsNum(int periodsNum) {
        this.periodsNum = periodsNum;
    }

    public int getScoresInPeriod() {
        return scoresInPeriod;
    }

    public void setScoresInPeriod(int scoresInPeriod) {
        this.scoresInPeriod = scoresInPeriod;
    }

    public String getKindOfSport() {
        return kindOfSport;
    }

    public void setKindOfSport(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }
}
