package com.br.positivityapp.models;

import java.util.ArrayList;

public class EventDiarySet {

    private ArrayList<EventDiary>eventsDiary;
    private int day;
    private int month;
    private int year;

    public EventDiarySet() {
    }

    public EventDiarySet(ArrayList<EventDiary> eventsDiary, int day, int month, int year) {
        this.eventsDiary = eventsDiary;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public ArrayList<EventDiary> getEventsDiary() {
        return eventsDiary;
    }

    public void setPositives(ArrayList<EventDiary> positives) {
        this.eventsDiary = positives;
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
}
