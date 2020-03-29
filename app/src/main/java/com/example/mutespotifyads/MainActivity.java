package com.example.mutespotifyads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void askForPermissions() {
        String notificationListenerString = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        if (notificationListenerString == null || !notificationListenerString.contains(getPackageName())) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        askForPermissions();
        startService(new Intent(this, SpotifyNotificationListenerService.class));
    }
}
