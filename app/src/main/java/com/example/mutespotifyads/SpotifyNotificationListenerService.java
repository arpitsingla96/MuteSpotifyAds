package com.example.mutespotifyads;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class SpotifyNotificationListenerService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private SpotifyHandler spotifyHandler = SpotifyHandler.getInstance();

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        spotifyHandler.setServiceContext(this);
    }

    private void handleNotificationPosted(StatusBarNotification sbn) {
        Log.i(TAG, "Received new notification");

        if (spotifyHandler.isSpotifyAdvertisement(sbn)) {
            spotifyHandler.mute();
        }
        else {
            spotifyHandler.unmute();
        }
    }

    private void handleNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "Notification removed");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        handleNotificationPosted(sbn);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn, RankingMap rankingMap) {
        handleNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        handleNotificationRemoved(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap) {
        handleNotificationRemoved(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        handleNotificationRemoved(sbn);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i(TAG, "Listener connected");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.i(TAG, "Listener disconnected");
    }
}
