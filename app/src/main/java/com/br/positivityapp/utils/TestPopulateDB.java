package com.br.positivityapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class TestPopulateDB {

    public TestPopulateDB() {
    }

    public void populateEventDiaryDB(Context c) {
        SQLiteDatabase db = SQLHelper.getMyInstance(c).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("description","bought a car");
        values.put("day", 10);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        values.put("latitude", 43.599245);
        values.put("longitude", -116.202088);

        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a ferrari");
        values.put("day", 10);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","15:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","won the lottery");
        values.put("day", 12);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a new console");
        values.put("day", 12);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a car");
        values.put("day", 12);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a new wallet");
        values.put("day", 12);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought another car");
        values.put("day", 13);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a computer");
        values.put("day", 13);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a new controller");
        values.put("day", 15);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a car");
        values.put("day", 15);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a car");
        values.put("day", 15);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a car");
        values.put("day", 16);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a car");
        values.put("day", 16);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a car");
        values.put("day", 17);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);

        values = new ContentValues();
        values.put("description","bought a car");
        values.put("day", 23);
        values.put("month",11);
        values.put("year",2016);
        values.put("time_creation","14:32");
        db.insert("EventDiary",null,values);
    }
}
