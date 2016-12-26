package work.technie.motonavigator.fragment;
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
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import work.technie.motonavigator.R;
import work.technie.motonavigator.adapter.DirectionAdapter;
import work.technie.motonavigator.data.MotorContract;
import work.technie.motonavigator.data.Steps;

/**
 * Created by anupam on 13/11/16.
 */

public class DriveFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int STEPS_LOADER = 1;
    private static final String SELECTED_KEY = "selected_position";

    private static final String[] STEPS_COLUMNS = {

            MotorContract.Steps.TABLE_NAME + "." + MotorContract.Steps._ID,
            MotorContract.Steps.ROUTE_ID,
            MotorContract.Steps.BEARING_BEFORE,
            MotorContract.Steps.BEARING_AFTER,
            MotorContract.Steps.LOCATION_LAT,
            MotorContract.Steps.LOCATION_LONG,
            MotorContract.Steps.TYPE,
            MotorContract.Steps.INSTRUCTION,
            MotorContract.Steps.MODE,
            MotorContract.Steps.DURATION,
            MotorContract.Steps.NAME,
            MotorContract.Steps.DISTANCE
    };

    public static int COL_STEPS_ID = 0;
    public static int COL_STEPS_ROUTE_ID = 1;
    public static int COL_STEPS_BEARING_BEFORE = 2;
    public static int COL_STEPS_BEARING_AFTER = 3;
    public static int COL_STEPS_LOCATION_LAT = 4;
    public static int COL_STEPS_LOCATION_LONG = 5;
    public static int COL_STEPS_TYPE = 6;
    public static int COL_STEPS_INSTRUCTION = 7;
    public static int COL_STEPS_MODE = 8;
    public static int COL_STEPS_DURATION = 9;
    public static int COL_STEPS_NAME = 10;
    public static int COL_STEPS_DISTANCE = 11;
    String routeId = null;
    DirectionAdapter directionListAdapter;
    ListView listViewDir;
    private ArrayList<Steps> dirList;
    private int mPosition = ListView.INVALID_POSITION;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.dir_list), dirList);
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            routeId = arguments.getString(Intent.EXTRA_TEXT);
        }
        View rootView = inflater.inflate(R.layout.fragment_nav, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.directions);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        directionListAdapter = new DirectionAdapter(getActivity(), null, 0);
        listViewDir = (ListView) rootView.findViewById(R.id.listview_dir);
        listViewDir.setAdapter(directionListAdapter);

        if (savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.dir_list))) {
            dirList = new ArrayList<>();
        } else {
            dirList = savedInstanceState.getParcelableArrayList(getString(R.string.dir_list));
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(STEPS_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != routeId) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            switch (id) {
                case STEPS_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MotorContract.Steps.buildStepsUriWithRouteId(routeId),
                            STEPS_COLUMNS,
                            null,
                            null,
                            null
                    );
                default:
                    //nothing
                    break;
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }
        switch (loader.getId()) {
            case STEPS_LOADER:
                if (data.moveToFirst()) {
                    do {
                        directionListAdapter.swapCursor(data);
                    }
                    while (data.moveToNext());
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Loader");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        directionListAdapter.swapCursor(null);
    }
}

