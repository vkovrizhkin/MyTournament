package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Standing {

    public int id;
    public int competitionId; // идентификатор соревнования
    public int teamId; //идентификатор команды
    public int points; // количество очков
    public int matchesPlayed; //сыграно матчей
    public int gs; //голов забито
    public int ga; //голов пропущено
    public int won; //матчей выиграно
    public int lost; //матчей проиграно

    public Standing(int id, int competitionId, int teamId, int points, int matchesPlayed, int gs, int ga, int won, int lost) {
        this.id = id;
        this.competitionId = competitionId;
        this.teamId = teamId;
        this.points = points;
        this.matchesPlayed = matchesPlayed;
        this.gs = gs;
        this.ga = ga;
        this.won = won;
        this.lost = lost;
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
