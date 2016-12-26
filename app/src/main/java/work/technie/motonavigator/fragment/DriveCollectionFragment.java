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
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import work.technie.motonavigator.R;
import work.technie.motonavigator.adapter.DirectionCollectionAdapter;
import work.technie.motonavigator.data.MotorContract;
import work.technie.motonavigator.data.Waypoints;
import work.technie.motonavigator.widget.CollectionWidgetProvider;

/**
 * Created by anupam on 14/11/16.
 */

public class DriveCollectionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int WAYPOINTS_LOADER = 0;
    private static final String SELECTED_KEY = "selected_position";

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
    DirectionCollectionAdapter directionCollectionAdapter;
    ListView listViewDirCollection;
    private ArrayList<Waypoints> dirCollectionList;
    private int mPosition = ListView.INVALID_POSITION;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getString(R.string.dir_collection_list), dirCollectionList);
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collection_drive, container, false);

        Activity mActivity = getActivity();
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.drive);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        directionCollectionAdapter = new DirectionCollectionAdapter(getActivity(), null, 0);
        listViewDirCollection = (ListView) rootView.findViewById(R.id.listview_collection_dir);
        listViewDirCollection.setAdapter(directionCollectionAdapter);
        listViewDirCollection.setEmptyView(rootView.findViewById(R.id.emptyElement));

        if (savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.dir_collection_list))) {
            dirCollectionList = new ArrayList<>();
        } else {
            dirCollectionList = savedInstanceState.getParcelableArrayList(getString(R.string.dir_collection_list));
        }

        listViewDirCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    ((Callback) getActivity())
                            .onItemSelected(cursor.getString(COL_WAYPOINTS_ROUTE_ID));
                }
                mPosition = position;
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(WAYPOINTS_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        switch (id) {
            case WAYPOINTS_LOADER:
                return new CursorLoader(
                        getActivity(),
                        MotorContract.Waypoints.buildWaypointUri(),
                        WAYPOINTS_COLUMNS,
                        null,
                        null,
                        null
                );
            default:
                //nothing
                break;
        }
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_collection, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
            final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.dialog_delete_title)
                    .setMessage(R.string.dialog_delete_msg)
                    .setPositiveButton(R.string.dialog_yes, null)
                    .setNegativeButton(R.string.dialog_no, null)
                    .create();
            dialog.show();

            final LoaderManager.LoaderCallbacks loaderCallbacks = this;

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Uri uri = MotorContract.Waypoints.buildWaypointUri();
                    getActivity().getContentResolver().delete(uri, null, null);
                    getActivity().sendBroadcast(new Intent(CollectionWidgetProvider.ACTION_UPDATE));
                    directionCollectionAdapter.swapCursor(null);
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }
        switch (loader.getId()) {
            case WAYPOINTS_LOADER:
                if (data.moveToFirst()) {
                    do {
                        directionCollectionAdapter.swapCursor(data);
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
        directionCollectionAdapter.swapCursor(null);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         *
         * @param route_id
         */
        void onItemSelected(String route_id);
    }
}

