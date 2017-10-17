package io.innovate.innovate.app;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by NottyNerd on 18/10/2016.
 */
public class INNovate extends Application {
    public FirebaseDatabase database;
    public DatabaseReference myQuoteRef;
    public DatabaseReference myInspirationRef;

    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Nickainley.ttf")
                .setFontAttrId(uk.co.chrisjenx.calligraphy.R.attr.fontPath)
                .build()
        );

        database = FirebaseDatabase.getInstance();
        myQuoteRef = database.getReference("Quote");
        myInspirationRef = database.getReference("Inspiration");



    }


}
