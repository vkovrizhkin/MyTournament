package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Match {

    public int id;
    public int competitionId; //идентификатор соревнования
    public String date; // дата матча
    public String stage; // этап соревнования (10 тур , полуфинал, групповой этап)
    public int team1Id;// принимающая команда
    public int team2Id; //команда гостей
    public String place; //место проведения
    public boolean played; //сыгран / несыгран

    public Match(int id, int competitionId, String date, String stage, int team1Id, int team2Id, String place, boolean played) {
        this.id = id;
        this.competitionId = competitionId;
        this.date = date;
        this.stage = stage;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.place = place;
        this.played = played;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
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
