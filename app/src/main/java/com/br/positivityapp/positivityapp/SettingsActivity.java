package com.br.positivityapp.positivityapp;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.br.positivityapp.R;
import com.br.positivityapp.utils.RemiderBroadcastReceiver;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SettingsActivity extends AppCompatActivity {

    public Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setChecked(checkIfReminderExists());

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    createReminder();
                } else {
                    cancelReminder();
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

//                        Intent intentResult = new Intent();
//                        intentResult.setData(MainActivity.SETTINGSACTIVITYCODELOGOUT);
                        setResult(MainActivity.SETTINGSACTIVITYCODELOGOUT);
                        finish();

                    }
                });



            }
        });



    }

    public void createReminder() {

        Intent intent = new Intent(this, RemiderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,100,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    public void cancelReminder() {
        Intent intent = new Intent(this, RemiderBroadcastReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_NO_CREATE);

        if (pintent != null) {

            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pintent);
            pintent.cancel();
        }
    }

    public boolean checkIfReminderExists() {
        Intent intent = new Intent(this, RemiderBroadcastReceiver.class);
        PendingIntent pintent = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_NO_CREATE);

        if (pintent != null) {
            return true;

        } else {
            return false;
        }
    }
}
