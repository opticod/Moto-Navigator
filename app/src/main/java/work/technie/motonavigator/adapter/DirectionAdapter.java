package work.technie.motonavigator.adapter;
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
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import work.technie.motonavigator.R;
import work.technie.motonavigator.fragment.DriveFragment;

/**
 * Created by anupam on 13/11/16.
 */

public class DirectionAdapter extends CursorAdapter {
    private static final String LOG_TAG = DirectionAdapter.class.getSimpleName();

    public DirectionAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_nav_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    /*
       This is where we fill-in the views with the contents of the cursor.
    */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String BEARING_BEFORE = cursor.getString(DriveFragment.COL_STEPS_BEARING_BEFORE);
        String BEARING_AFTER = cursor.getString(DriveFragment.COL_STEPS_BEARING_AFTER);
        String LOCATION_LAT = cursor.getString(DriveFragment.COL_STEPS_LOCATION_LAT);
        String LOCATION_LONG = cursor.getString(DriveFragment.COL_STEPS_LOCATION_LONG);

        String STEPS_TYPE = cursor.getString(DriveFragment.COL_STEPS_TYPE);
        String STEPS_INSTRUCTION = cursor.getString(DriveFragment.COL_STEPS_INSTRUCTION);
        String STEPS_MODE = cursor.getString(DriveFragment.COL_STEPS_MODE);
        double STEPS_DURATION = cursor.getDouble(DriveFragment.COL_STEPS_DURATION);
        String STEPS_NAME = cursor.getString(DriveFragment.COL_STEPS_NAME);
        double STEPS_DISTANCE = cursor.getDouble(DriveFragment.COL_STEPS_DISTANCE);

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.type.setText(firstCapital(STEPS_TYPE));
        if (null != viewHolder.name && !STEPS_NAME.isEmpty()) {

            viewHolder.name.setText(firstCapital(STEPS_NAME));

        } else if (null != viewHolder.name) {

            LinearLayout.LayoutParams paramIns = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0, 2.0f);

            LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0, 0f);

            paramIns.gravity = Gravity.CENTER;
            paramIns.topMargin = 28;
            viewHolder.name.setLayoutParams(paramName);
            viewHolder.instruction.setLayoutParams(paramIns);

        }

        viewHolder.instruction.setText(firstCapital(STEPS_INSTRUCTION));
        if (null != viewHolder.mode) {
            STEPS_MODE = STEPS_MODE.toLowerCase();
            viewHolder.mode.setImageDrawable(ContextCompat.getDrawable(context, STEPS_MODE.equals(context.getString(R.string.cycling)) ? R.drawable.ic_directions_bike_black_24dp : (STEPS_MODE.equals(context.getString(R.string.driving)) ? R.drawable.ic_drive_eta_black_24dp : R.drawable.ic_directions_walk_black_24dp)));
        }
        if (STEPS_DISTANCE >= 500) {
            STEPS_DISTANCE /= 1000;
            viewHolder.distance.setText(String.format(Locale.US, "%.2f" + context.getString(R.string.km), STEPS_DISTANCE));
        } else {
            viewHolder.distance.setText(String.format(Locale.US, "%.0f" + context.getString(R.string.m), STEPS_DISTANCE));
        }
        if (STEPS_DURATION >= 60) {
            int min = (int) Math.floor(STEPS_DURATION / 60);
            int sec = (int) (STEPS_DURATION - min * 60);
            viewHolder.duration.setText(String.format(Locale.US, "%d " + context.getString(R.string.min) + " %d" + context.getString(R.string.sec), min, sec));
        } else {
            viewHolder.duration.setText(String.format(Locale.US, "%d" + context.getString(R.string.sec), (int) STEPS_DURATION));
        }

    }

    public String firstCapital(String str) {
        return str.length() > 0 ? str.substring(0, 1).toUpperCase() + (str.length() > 1 ? str.substring(1) : " ") : " ";
    }

    public static class ViewHolder {

        public final TextView type;
        public final TextView name;
        public final TextView instruction;
        public final ImageView mode;
        public final TextView distance;
        public final TextView duration;

        public ViewHolder(View view) {
            type = (TextView) view.findViewById(R.id.type);
            name = (TextView) view.findViewById(R.id.name);
            instruction = (TextView) view.findViewById(R.id.instruction);
            mode = (ImageView) view.findViewById(R.id.mode);
            distance = (TextView) view.findViewById(R.id.distance);
            duration = (TextView) view.findViewById(R.id.duration);
        }
    }
}