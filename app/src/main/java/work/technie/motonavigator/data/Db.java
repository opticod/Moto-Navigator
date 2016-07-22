/*
 * Copyright (c) 2016 Anupam Das.
 */

package work.technie.motonavigator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * Created by anupam on 21/7/16.
 */

public class Db {

    private static final String EQUAL = " == ";
    private final DBHelper dbHelper;
    private SQLiteDatabase db;

    public Db(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public boolean isOpen() {
        return db.isOpen();
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor getRoutesCursor() {

        return db.query(
                DbContract.Route.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getStepsCursor() {

        return db.query(
                DbContract.Steps.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getRouteCursorById(int id) {

        String selection = DbContract.Route._ID + EQUAL + id;

        return db.query(
                DbContract.Route.TABLE_NAME,
                null,
                selection,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getStepCursorById(int id) {

        String selection = DbContract.Steps._ID + EQUAL + id;

        return db.query(
                DbContract.Steps.TABLE_NAME,
                null,
                selection,
                null,
                null,
                null,
                null
        );
    }

    public long getCountRoute() {

        return DatabaseUtils.queryNumEntries(db,
                DbContract.Route.TABLE_NAME);
    }

    public long getCountSteps() {

        return DatabaseUtils.queryNumEntries(db,
                DbContract.Steps.TABLE_NAME);
    }

    public int bulkInsertRoute(@NonNull ContentValues[] values) {

        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {

                long _id = db.insert(DbContract.Route.TABLE_NAME, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return returnCount;
    }

    public int bulkInsertSteps(@NonNull ContentValues[] values) {

        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {

                long _id = db.insert(DbContract.Steps.TABLE_NAME, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return returnCount;
    }

    public int bulkInsertRouteSteps(@NonNull ContentValues route, ContentValues[] steps) {

        db.beginTransaction();
        int returnCount = 0;
        try {
            long _id = db.insert(DbContract.Route.TABLE_NAME, null, route);
            if (_id != -1) {
                returnCount++;
            }
            for (ContentValues step : steps) {

                _id = db.insert(DbContract.Steps.TABLE_NAME, null, step);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return returnCount;
    }

    public void clearDatabaseTable(String table) {
        db.beginTransaction();
        try {
            db.delete(table, null, null);
            db.execSQL("delete from sqlite_sequence where name='" + table + "';");
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }
}
