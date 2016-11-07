
package work.technie.motonavigator.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MotorContract {

    public static final String CONTENT_AUTHORITY = "work.technie.motonavigator";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_WAYPOINTS = "waypoints";
    public static final String PATH_STEPS = "steps";

    public static final class Waypoints implements BaseColumns {
        public static final String TABLE_NAME = "Waypoints";
        public static final String START_NAME = "start_name";
        public static final String START_LAT = "start_lat";
        public static final String START_LONG = "start_long";
        public static final String DEST_NAME = "dest_name";
        public static final String DEST_LAT = "dest_lat";
        public static final String DEST_LONG = "dest_long";
        public static final String ROUTE_A_ID = "route_a_id";
        public static final String ROUTE_A_DURATION = "route_a_duration";
        public static final String ROUTE_B_ID = "route_b_id";
        public static final String ROUTE_B_DURATION = "route_b_duration";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WAYPOINTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WAYPOINTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WAYPOINTS;

        public static Uri buildWaypointsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../waypoints/waypointId
        public static Uri buildWaypointUriWithWaypointId(String waypointId) {
            return CONTENT_URI.buildUpon().appendPath(waypointId).build();
        }

        public static Uri buildWaypointUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


    }

    public static final class Steps implements BaseColumns {

        public static final String TABLE_NAME = "Steps";
        public static final String ROUTE_ID = "route_id";
        public static final String DURATION_ROUTE = "duration_route";
        public static final String DISTANCE_ROUTE = "distance_route";

        public static final String BEARING_BEFORE = "bearing_before";
        public static final String BEARING_AFTER = "bearing_after";
        public static final String LOCATION_LAT = "location_lat";
        public static final String LOCATION_LONG = "location_long";
        public static final String TYPE = "type";
        public static final String INSTRUCTION = "instruction";
        public static final String MODE = "mode";
        public static final String DURATION = "duration";
        public static final String NAME = "name";
        public static final String DISTANCE = "distance";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEPS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WAYPOINTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WAYPOINTS;

        public static Uri buildStepsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        //content://work....../steps/stepsId
        public static Uri buildStepsUriWithStepId(String stepId) {
            return CONTENT_URI.buildUpon().appendPath(stepId).build();
        }

        public static Uri buildStepUri() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getIDFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}
