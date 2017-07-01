package com.br.positivityapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.br.positivityapp.models.EventDiary;
import com.br.positivityapp.models.EventDiarySet;
import com.br.positivityapp.utils.SQLHelper;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

public class EventDiarySetDAO {

    private SQLHelper helper;

    public EventDiarySetDAO(Context context) {
        helper = SQLHelper.getMyInstance(context);
    }

    /**
     * returns all EventsDiary from the db
     * @return ArrayList os EventDiary
     */
    public ArrayList<EventDiarySet> getAllEventDiarySet() {
        ArrayList<EventDiary> allPos = new ArrayList<>();
        ArrayList<EventDiarySet> allPosDaySet = new ArrayList<>();

        int dayBefore = 0;
        int monthBefore = 0;
        int yearBefore = 0;

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(false,"EventDiary",new String[]{},null,null,null,null,"YEAR, MONTH, DAY",null);
        c.moveToFirst();

        while (!c.isAfterLast()){

            int id = c.getInt(0);
            String description = c.getString(1);
            int day = c.getInt(2);
            int month = c.getInt(3);
            int year = c.getInt(4);
            String time = c.getString(5);
            Double latitude = c.getDouble(6);
            Double longitude = c.getDouble(7);

            EventDiary eventDiary = new EventDiary(id,description,day,month,year,time, latitude, longitude);

            if (dayBefore == 0) {
                dayBefore = day;
                monthBefore = month;
                yearBefore = year;
            }

            if (day != dayBefore || month != monthBefore || year != yearBefore) {
                allPosDaySet.add(new EventDiarySet(allPos,dayBefore,monthBefore,yearBefore));

                dayBefore = day;
                monthBefore = month;
                yearBefore = year;
                allPos = new ArrayList<>();
                allPos.add(eventDiary);

            } else {
                allPos.add(eventDiary);
            }
            c.moveToNext();
        }
        if (allPos.size() > 0) {
            allPosDaySet.add(new EventDiarySet(allPos, dayBefore, monthBefore, yearBefore));
        }
        c.close();

        return allPosDaySet;
    }

    /**
     * Insert EventDiary in database
     * @param eventDiary EventDiary
     * @param context Context
     */
    public void insertEventDiary(EventDiary eventDiary, Context context) {
        ContentValues values = new ContentValues();
        values.put("description",eventDiary.getDescription());
        values.put("day",eventDiary.getDay());
        values.put("month",eventDiary.getMonth());
        values.put("year",eventDiary.getYear());
        values.put("time_creation",eventDiary.getTime());
        values.put("latitude", eventDiary.getLatitude());
        values.put("longitude", eventDiary.getLongitude());

        SQLiteDatabase db = SQLHelper.getMyInstance(context).getWritableDatabase();
        db.insert("EventDiary", null, values);
    }

    public ArrayList<EventDiary> getEventDiaryByDate(CalendarDay daySelected) {
        String day = Integer.toString(daySelected.getDay());
        String month = Integer.toString(daySelected.getMonth());
        String year = Integer.toString(daySelected.getYear());

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(false,"EventDiary",new String[]{},"day = ? AND month = ? AND year = ?",new String[]{day,month,year},null,null,null,null);
        c.moveToFirst();

        ArrayList<EventDiary> allEvents = new ArrayList<>();
        while (!c.isAfterLast()) {

            int id = c.getInt(0);
            String description = c.getString(1);
            int dayC = c.getInt(2);
            int monthC = c.getInt(3);
            int yearC = c.getInt(4);
            String time = c.getString(5);
            Double latitude = c.getDouble(6);
            Double longitude = c.getDouble(7);

            EventDiary eventDiary = new EventDiary(id, description, dayC, monthC, yearC, time, latitude, longitude);
            allEvents.add(eventDiary);

            c.moveToNext();
        }
        return allEvents;
    }
}
