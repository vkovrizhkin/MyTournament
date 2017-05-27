package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Player {

    private int id;

    private String fio; // ФИО игрока

    private int teamId; //идентификатор команды игрока

    private String info; //дополнительная информация

    private int number; //номер

    private String dateBorn; //дата рождения

    public Player(int id, String fio, int teamId, String info, int number, String dateBorn) {
        this.id = id;
        this.fio = fio;
        this.teamId = teamId;
        this.info = info;
        this.number = number;
        this.dateBorn = dateBorn;
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

    public String getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(String dateBorn) {
        this.dateBorn = dateBorn;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
