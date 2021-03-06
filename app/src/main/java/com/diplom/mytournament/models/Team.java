package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Team {

    private int id;

    private String name; //название команды

    private String logoResourceId; // ссылка на ресурс логотипа команды

    private String kindOfSport; // вид спорта (футбол/ минифутбол)

    private String info; // дополнительная информация

    public Team(int id, String name, String logoResourceId, String kindOfSport, String info) {
        this.id = id;
        this.name = name;
        this.logoResourceId = logoResourceId;
        this.kindOfSport = kindOfSport;
        this.info = info;
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

    public String getLogoResourceId() {
        return logoResourceId;
    }

    public void setLogoResourceId(String logoResourceId) {
        this.logoResourceId = logoResourceId;
    }

    public String getKindOfSport() {
        return kindOfSport;
    }

    public void setKindOfSport(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
