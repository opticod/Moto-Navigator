package work.technie.motonavigator.data;
/*
 * Copyright (C) 2017 Anupam Das
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MotorProvider extends ContentProvider {

    static final int WAYPOINTS = 100;
    static final int WAYPOINTS_WITH_ID = 101;
    static final int STEPS = 200;
    static final int STEPS_WITH_ROUTE_ID = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MotorDBHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MotorContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MotorContract.PATH_WAYPOINTS, WAYPOINTS);
        matcher.addURI(authority, MotorContract.PATH_WAYPOINTS + "/*", WAYPOINTS_WITH_ID);

        matcher.addURI(authority, MotorContract.PATH_STEPS, STEPS);
        matcher.addURI(authority, MotorContract.PATH_STEPS + "/*", STEPS_WITH_ROUTE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MotorDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case WAYPOINTS:
                return MotorContract.Waypoints.CONTENT_TYPE;
            case WAYPOINTS_WITH_ID:
                return MotorContract.Waypoints.CONTENT_ITEM_TYPE;
            case STEPS:
                return MotorContract.Steps.CONTENT_TYPE;
            case STEPS_WITH_ROUTE_ID:
                return MotorContract.Steps.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case WAYPOINTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MotorContract.Waypoints.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case WAYPOINTS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MotorContract.Waypoints.TABLE_NAME,
                        projection,
                        MotorContract.Waypoints._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            case STEPS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MotorContract.Steps.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case STEPS_WITH_ROUTE_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MotorContract.Steps.TABLE_NAME,
                        projection,
                        MotorContract.Steps.ROUTE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case WAYPOINTS: {
                long _id = db.insert(MotorContract.Waypoints.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MotorContract.Waypoints.buildWaypointsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case STEPS: {
                long _id = db.insert(MotorContract.Steps.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MotorContract.Steps.buildStepsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case STEPS:
                rowsDeleted = db.delete(
                        MotorContract.Steps.TABLE_NAME, selection, selectionArgs);
                break;
            case STEPS_WITH_ROUTE_ID:
                rowsDeleted = db.delete(MotorContract.Steps.TABLE_NAME,
                        MotorContract.Steps.ROUTE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            case WAYPOINTS:
                rowsDeleted = db.delete(
                        MotorContract.Waypoints.TABLE_NAME, selection, selectionArgs);
                break;
            case WAYPOINTS_WITH_ID:
                rowsDeleted = db.delete(MotorContract.Waypoints.TABLE_NAME,
                        MotorContract.Waypoints._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case WAYPOINTS:
                rowsUpdated = db.update(MotorContract.Waypoints.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case WAYPOINTS_WITH_ID: {
                rowsUpdated = db.update(MotorContract.Waypoints.TABLE_NAME,
                        values,
                        MotorContract.Waypoints._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case STEPS:
                rowsUpdated = db.update(MotorContract.Steps.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case STEPS_WITH_ROUTE_ID: {
                rowsUpdated = db.update(MotorContract.Steps.TABLE_NAME,
                        values,
                        MotorContract.Steps.ROUTE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WAYPOINTS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MotorContract.Waypoints.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case STEPS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MotorContract.Steps.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}