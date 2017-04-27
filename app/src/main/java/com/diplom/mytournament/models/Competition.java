package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Competition {

    public int id;
    public String name; //название соревнования
    public String kindOfSport; //вид спорта
    public String info; //дополнительная информация
    public String date; //дата начала
    public String type; //тип расписания (кубок, по кругу и тд)
    public long logoIdResource;//ссылка на ресурс логотипа

    public Competition(String name, int id, String kindOfSport, String info, String date,
                       String type, long logoIdResource) {
        this.name = name;
        this.id = id;
        this.kindOfSport = kindOfSport;
        this.info = info;
        this.date = date;
        this.type = type;
        this.logoIdResource = logoIdResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLogoIdResource() {
        return logoIdResource;
    }

    public void setLogoIdResource(long logoIdResource) {
        this.logoIdResource = logoIdResource;
    }
}
