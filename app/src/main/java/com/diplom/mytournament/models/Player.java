package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Player {

    public int id;
    public String fio; // ФИО игрока
    public int teamId; //идентификатор команды игрока
    public String info; //дополнительная информация

    public Player(int id, String fio, int teamId, String info) {
        this.id = id;
        this.fio = fio;
        this.teamId = teamId;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
