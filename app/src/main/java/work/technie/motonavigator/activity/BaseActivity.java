package work.technie.motonavigator.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import work.technie.motonavigator.R;
import work.technie.motonavigator.auth.AuthActivity;

/**
 * Created by anupam on 31/10/16.
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String FRAGMENT_TAG_MAP = "MAP_FRAGMENT";
    private final static String STATE_FRAGMENT = "stateFragment";
    private static final String TAG = "BaseActivity";

    private final String FRAGMENT_TAG_REST = "FTAGR";
    private int currentMenuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void doMenuAction(int menuItemId) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (menuItemId) {
            case R.id.nav_map:
                /*
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, new MapFragment(), FRAGMENT_TAG_MAP).commit();
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Map");
                }*/
                navigationView.getMenu().getItem(0).setChecked(true);

                Intent intent = new Intent(this, MapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);

                break;
            case R.id.nav_directions:
                //Add transaction to navigation fragment
                break;
            case R.id.nav_settings:
                //Add transaction to settings fragment
                break;
            case R.id.nav_about:
                navigationView.getMenu().getItem(3).setChecked(true);
                intent = new Intent(this, AboutActivity.class);
                this.startActivity(intent);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("About");
                }
                break;
            case R.id.nav_sign_out:
                signOut();
                break;
            default:
                //nothing;
        }
    }

    private void signOut() {

        // Firebase sign out

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int SIGN_IN_MODE = sharedPref.getInt("LOGIN_MODE", -1);
        // Google sign out
        switch (SIGN_IN_MODE) {
            case -1:
                Intent intent = new Intent(this, AuthActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;

            case 0:
                //nothing
                break;
            case 1:
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {

                            }
                        });
                break;
            case 2:
                //nothing
                break;

            default:
                //
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("LOGIN_MODE", -1);
        editor.apply();

        Intent intent = new Intent(this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        doMenuAction(id);
        currentMenuItemId = id;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_FRAGMENT, currentMenuItemId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
