/*
 * Copyright (c) 2016 Anupam Das.
 */

package work.technie.motonavigator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import work.technie.motonavigator.data.DbContract.Route;
import work.technie.motonavigator.data.DbContract.Steps;

/**
 * Created by anupam on 21/7/16.
 */

class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moto_navigator.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE__TABLE_ROUTE = "CREATE TABLE " + Route.TABLE_NAME + " (" +
                Route._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Route.DURATION + "  TEXT," +
                Route.DISTANCE + "  TEXT)";

        final String SQL_CREATE__TABLE_STEPS = "CREATE TABLE " + Steps.TABLE_NAME + " (" +
                Steps._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Steps.BEARING_BEFORE + "  TEXT," +
                Steps.BEARING_AFTER + "  TEXT," +
                Steps.LOCATION_LAT + "  TEXT," +
                Steps.LOCATION_LONG + "  TEXT," +
                Steps.TYPE + "  TEXT," +
                Steps.INSTRUCTION + "  TEXT," +
                Steps.MODE + "  TEXT," +
                Steps.DURATION + "  TEXT," +
                Steps.NAME + "  TEXT," +
                Steps.DISTANCE + "  TEXT)";

        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_ROUTE);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_STEPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Route.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Steps.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}