
package work.technie.motonavigator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import work.technie.motonavigator.data.MotorContract.Steps;
import work.technie.motonavigator.data.MotorContract.Waypoints;

public class MotorDBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "motorNavigator.db";
    private static final int DATABASE_VERSION = 1;

    public MotorDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE__TABLE_WAYPOINTS = "CREATE TABLE " + Waypoints.TABLE_NAME + " (" +
                Waypoints._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Waypoints.START_NAME + "  TEXT," +
                Waypoints.START_LAT + "  TEXT," +
                Waypoints.START_LONG + "  TEXT," +
                Waypoints.DEST_NAME + "  TEXT," +
                Waypoints.DEST_LAT + "  TEXT," +
                Waypoints.DEST_LONG + "  TEXT," +
                Waypoints.MODE + "  TEXT," +
                Waypoints.ROUTE_ID + "  TEXT," +
                Waypoints.ROUTE_DURATION + "  TEXT," +
                Waypoints.ROUTE_DISTANCE + " TEXT)";

        final String SQL_CREATE__TABLE_STEPS = "CREATE TABLE " + Steps.TABLE_NAME + " (" +
                Steps._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Steps.ROUTE_ID + "  TEXT," +
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

        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_WAYPOINTS);
        sqLiteDatabase.execSQL(SQL_CREATE__TABLE_STEPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Steps.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Waypoints.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}