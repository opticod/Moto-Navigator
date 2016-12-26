package work.technie.motonavigator.activity;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import work.technie.motonavigator.R;
import work.technie.motonavigator.auth.AuthActivity;

/**
 * Created by anupam on 7/11/16.
 */
public class LaunchScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, getString(R.string.onAuthStateChanges_signed_in) + user.getUid());
            new Wait(true).execute();
        } else {
            Log.d(TAG, getString(R.string.onAuthStateChanges_sign_out));
            new Wait(false).execute();
        }

    }

    public class Wait extends AsyncTask<Void, Void, Void> {

        private boolean loggedIn;

        Wait(boolean loggedIn) {
            this.loggedIn = loggedIn;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            Intent intent = new Intent(LaunchScreenActivity.this, loggedIn ? BaseActivity.class : AuthActivity.class);
            startActivity(intent);
            finish();
        }
    }
}