package com.example.mutespotifyads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

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

    private void buttons() {
        findViewById(R.id.start_listening).setOnClickListener(v -> startService(new Intent(this, SpotifyNotificationListenerService.class)));
        findViewById(R.id.get_service_status).setOnClickListener(v -> {
            TextView serviceStatus = findViewById(R.id.service_status);
            if (SpotifyNotificationListenerService.isRunning()) {
                serviceStatus.setText(R.string.service_running);
            }
            else {
                serviceStatus.setText(R.string.service_not_running);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        askForPermissions();
        buttons();
    }
}
