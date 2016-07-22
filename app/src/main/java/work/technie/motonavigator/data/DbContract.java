/*
 * Copyright (c) 2016 Anupam Das.
 */

package work.technie.motonavigator.data;

import android.provider.BaseColumns;

/**
 * Created by anupam on 21/7/16.
 */

public class DbContract {

    public static final class Steps implements BaseColumns {

        public static final String TABLE_NAME = "Steps";

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

    }

    public static final class Route implements BaseColumns {

        public static final String TABLE_NAME = "Route";

        public static final String DURATION = "duration";
        public static final String DISTANCE = "distance";

    }
}
