package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Event {

    public int id;
    public int matchId; // идентификатор матча
    public int playerId; //идентификатор игрока
    public String type; // тип события (гол, жёлтая/красная карточка, фол, пенальти, сейв ...)
    public int minute; //минута матча

    public Event(int id, int matchId, int playerId, String type, int minute) {
        this.id = id;
        this.matchId = matchId;
        this.playerId = playerId;
        this.type = type;
        this.minute = minute;
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
}
