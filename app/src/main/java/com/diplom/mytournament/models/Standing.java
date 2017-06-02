package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Standing {

    private int id;

    private int competitionId; // идентификатор соревнования

    private int teamId; //идентификатор команды

    private int points; // количество очков

    private int matchesPlayed; //сыграно матчей

    private int gs; //голов забито

    private int ga; //голов пропущено

    private int won; //матчей выиграно

    private int lost; //матчей проиграно

    private int drawn;//количество ничьих

    private String group;//группа (если есть)


    public Standing(int id, int competitionId, int teamId, int points, int matchesPlayed, int gs, int ga, int won, int lost, int drawn, String group) {
        this.id = id;
        this.competitionId = competitionId;
        this.teamId = teamId;
        this.points = points;
        this.matchesPlayed = matchesPlayed;
        this.gs = gs;
        this.ga = ga;
        this.won = won;
        this.lost = lost;
        this.drawn = drawn;
        this.group = group;
    }

    public int getDrawn() {
        return drawn;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getGs() {
        return gs;
    }

    public void setGs(int gs) {
        this.gs = gs;
    }

    public int getGa() {
        return ga;
    }

    public void setGa(int ga) {
        this.ga = ga;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }
}
