/*
 * Copyright (c) 2016 Anupam Das.
 */

package work.technie.motonavigator;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.mapbox.mapboxsdk.MapboxAccountManager;

/**
 * Created by anupam on 20/7/16.
 */
public class MotoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MapboxAccountManager.start(this, getString(R.string.PUBLIC_TOKEN));
        Stetho.initializeWithDefaults(this);

    }
}