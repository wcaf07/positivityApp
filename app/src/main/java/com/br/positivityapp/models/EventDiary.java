package com.br.positivityapp.models;

public class EventDiary {

    private int id;
    private String description;
    private int day;
    private int month;
    private int year;
    private String time;
    private Double latitude;
    private Double longitude;

    public EventDiary() {
    }

    public EventDiary(int id, String description, int day, int month, int year, String time, Double latitude, Double longitude) {
        this.id = id;
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public EventDiary(String description, int day, int month, int year, String time, Double latitude, Double longitude) {
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public EventDiary(String description, int day, int month, int year, String time) {
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }
}
