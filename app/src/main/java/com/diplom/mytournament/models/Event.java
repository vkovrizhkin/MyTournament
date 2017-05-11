package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Event {

    private int id;

    private int matchId; // идентификатор матча

    private int playerId; //идентификатор игрока

    private String type; // тип события (гол, жёлтая/красная карточка, фол, пенальти, сейв ...)

    private int minute; //минута матча

    private char side;


    public Event(int id, int matchId, int playerId, String type, int minute, char side) {
        this.id = id;
        this.matchId = matchId;
        this.playerId = playerId;
        this.type = type;
        this.minute = minute;
        this.side = side;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }
}
