package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Match {

    private int id;

    private long competitionId; //идентификатор соревнования

    private String date; // дата матча

    private String stage; // этап соревнования (10 тур , полуфинал, групповой этап)

    private int team1Id;// принимающая команда

    private int team2Id; //команда гостей

    private int scores1 = 0;//количество голов, забитых первой командой

    private int scores2 = 0;//второй командой

    private String place; //место проведения

    private int played; //сыгран / несыгран

    public Match(int id, long competitionId, String date, String stage, int team1Id, int team2Id,
                 String place, int played, int scores1, int scores2) {
        this.id = id;
        this.competitionId = competitionId;
        this.date = date;
        this.stage = stage;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.place = place;
        this.played = played;
        this.scores1 = scores1;
        this.scores2 = scores2;
    }

    public int getScores1() {
        return scores1;
    }

    public void setScores1(int scores1) {
        this.scores1 = scores1;
    }

    public int getScores2() {
        return scores2;
    }

    public void setScores2(int scores2) {
        this.scores2 = scores2;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(long competitionId) {
        this.competitionId = competitionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public int getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(int team1Id) {
        this.team1Id = team1Id;
    }

    public int getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(int team2Id) {
        this.team2Id = team2Id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
