package work.technie.motonavigator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import work.technie.motonavigator.R;
import work.technie.motonavigator.auth.AuthActivity;

/**
 * Created by anupam on 7/11/16.
 */
public class LaunchScreenActivity extends Activity {

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