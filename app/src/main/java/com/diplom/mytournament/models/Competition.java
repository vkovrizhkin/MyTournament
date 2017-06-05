package com.diplom.mytournament.models;

/**
 * Created by Вова on 10.04.2017.
 */

public class Competition {

    private long id;

    private String name; //название соревнования

    private int format; //вид спорта

    private String info; //дополнительная информация

    private String date; //дата начала

    private String type; //тип расписания (кубок, по кругу и тд)

    private String logoIdResource;//ссылка на ресурс логотипа

    public Competition() {
    }

    public Competition(String name, long id, int format, String info, String date,
                       String type, String logoIdResource) {
        this.name = name;
        this.id = id;
        this.format = format;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
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

    public String getLogoIdResource() {
        return logoIdResource;
    }

    public void setLogoIdResource(String logoIdResource) {
        this.logoIdResource = logoIdResource;
    }
}
