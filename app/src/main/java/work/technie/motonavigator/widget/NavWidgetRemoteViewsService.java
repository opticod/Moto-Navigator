package work.technie.motonavigator.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import work.technie.motonavigator.R;
import work.technie.motonavigator.data.MotorContract;

/**
 * Created by anupam on 16/11/16.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NavWidgetRemoteViewsService extends RemoteViewsService {

    private static final String[] WAYPOINTS_COLUMNS = {

            MotorContract.Waypoints.TABLE_NAME + "." + MotorContract.Waypoints._ID,
            MotorContract.Waypoints.START_NAME,
            MotorContract.Waypoints.START_LAT,
            MotorContract.Waypoints.START_LONG,
            MotorContract.Waypoints.DEST_NAME,
            MotorContract.Waypoints.DEST_LAT,
            MotorContract.Waypoints.DEST_LONG,
            MotorContract.Waypoints.MODE,
            MotorContract.Waypoints.ROUTE_ID,
            MotorContract.Waypoints.ROUTE_DURATION,
            MotorContract.Waypoints.ROUTE_DISTANCE
    };

    public static int COL_WAYPOINTS_ID = 0;
    public static int COL_WAYPOINTS_START_NAME = 1;
    public static int COL_WAYPOINTS_START_LAT = 2;
    public static int COL_WAYPOINTS_START_LONG = 3;
    public static int COL_WAYPOINTS_DEST_NAME = 4;
    public static int COL_WAYPOINTS_DEST_LAT = 5;
    public static int COL_WAYPOINTS_DEST_LONG = 6;
    public static int COL_WAYPOINTS_MODE = 7;
    public static int COL_WAYPOINTS_ROUTE_ID = 8;
    public static int COL_WAYPOINTS_ROUTE_DURATION = 9;
    public static int COL_WAYPOINTS_ROUTE_DISTANCE = 10;
    private String start_name;
    private String dest_name;
    private String mode;
    private double route_duration;
    private double route_distance;
    private String route_id;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteViewsFactory() {
            private MatrixCursor data = null;

            @Override
            public void onCreate() {
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                Uri uri = MotorContract.Waypoints.buildWaypointUri();
                Cursor cursor = getApplicationContext().getContentResolver().query(uri, WAYPOINTS_COLUMNS, null, null, null);
                String[] columns = new String[]{"mode", "start_name", "end_name", "route_id"};
                data = new MatrixCursor(columns);
                if (cursor != null) {
                    cursor.moveToFirst();
                    try {
                        while (cursor.moveToNext()) {
                            data.addRow(new Object[]{cursor.getString(COL_WAYPOINTS_MODE), cursor.getString(COL_WAYPOINTS_START_NAME), cursor.getString(COL_WAYPOINTS_DEST_NAME), cursor.getLong(COL_WAYPOINTS_ROUTE_ID)});
                        }
                    } finally {
                        cursor.close();
                    }
                }
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }

                mode = data.getString(0);
                start_name = data.getString(1);
                dest_name = data.getString(2);
                route_id = data.getString(3);


                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_list_item);

                if (mode.equalsIgnoreCase("driving")) {
                    views.setViewVisibility(R.id.modeCar, View.VISIBLE);
                    views.setViewVisibility(R.id.modeBike, View.GONE);
                    views.setViewVisibility(R.id.modeWalk, View.GONE);
                } else if (mode.equalsIgnoreCase("cycling")) {
                    views.setViewVisibility(R.id.modeCar, View.GONE);
                    views.setViewVisibility(R.id.modeBike, View.VISIBLE);
                    views.setViewVisibility(R.id.modeWalk, View.GONE);
                } else if (mode.equalsIgnoreCase("walking")) {
                    views.setViewVisibility(R.id.modeCar, View.GONE);
                    views.setViewVisibility(R.id.modeBike, View.GONE);
                    views.setViewVisibility(R.id.modeWalk, View.VISIBLE);
                }

                views.setTextViewText(R.id.start_name, start_name);
                views.setTextViewText(R.id.dest_name, dest_name);

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra("route_id", route_id);
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(3);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

        };
    }
}
