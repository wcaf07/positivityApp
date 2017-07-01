package com.br.positivityapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

    private static SQLHelper myInstance = null;
    private static final String DATABASE_NAME = "positivity.db";
    private static final int VERSION = 2;
    private final String CREATE_TABLE_POS = "CREATE TABLE EVENTDIARY ("+
            "  ID INTEGER PRIMARY KEY AUTOINCREMENT"+
            ", DESCRIPTION TEXT NOT NULL" +
            ", DAY INTEGER NOT NULL" +
            ", MONTH INTEGER NOT NULL" +
            ", YEAR INTEGER NOT NULL" +
            ", TIME_CREATION TEXT NOT NULL" +
            ", LATITUDE DOUBLE" +
            ", LONGITUDE DOUBLE" +
            ");";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static SQLHelper getMyInstance(Context context) {
        if (myInstance == null) {
            myInstance = new SQLHelper(context);
        }
        return myInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_POS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        while (oldVersion < newVersion) {
            if (oldVersion == 1) {
                db.execSQL("ALTER TABLE EVENTDIARY ADD COLUMN LATITUDE DOUBLE");
                db.execSQL("ALTER TABLE EVENTDIARY ADD COLUMN LONGITUDE DOUBLE");
            }
            oldVersion++;
        }
    }
}
