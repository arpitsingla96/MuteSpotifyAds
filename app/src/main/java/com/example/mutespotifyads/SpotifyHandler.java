package com.example.mutespotifyads;

import android.app.Notification;
import android.content.Context;
import android.media.AudioManager;
import android.service.notification.StatusBarNotification;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.AUDIO_SERVICE;

public class SpotifyHandler {
    private static SpotifyHandler instance;

    private final String TAG = this.getClass().getSimpleName();
    private Context serviceContext;

    private static final String PACKAGE_NAME = "com.spotify.music";
    private static final List<String> ADVERTISEMENT_TICKER_TEXTS = Arrays.asList("Advertisement", "Spotify");

    private int lastVol = 0;

    private SpotifyHandler() {
    }


    public boolean isSpotifyAdvertisement(StatusBarNotification sbn) {
        return isSpotifyNotification(sbn) && isAdvertisement(sbn.getNotification());
    }

    public boolean isSpotifyNotification(StatusBarNotification sbn) {
        return PACKAGE_NAME.equals(sbn.getPackageName());
    }

    public boolean isAdvertisement(Notification notification) {
        if (notification.tickerText == null) return false;
        return ADVERTISEMENT_TICKER_TEXTS.contains(notification.tickerText.toString());
    }


    public static SpotifyHandler getInstance() {
        if (instance == null) {
            synchronized (SpotifyHandler.class) {
                if (instance == null) {
                    instance = new SpotifyHandler();
                }

            }
        }
        return instance;
    }


    public void setServiceContext(Context serviceContext) {
        this.serviceContext = serviceContext;
    }

    private int getVolume() {
        AudioManager am = (AudioManager) serviceContext.getSystemService(AUDIO_SERVICE);
        return am.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    private void setVolume(int vol) {
        AudioManager am = (AudioManager) serviceContext.getSystemService(AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, vol, AudioManager.FLAG_SHOW_UI);
    }

    private boolean isMute() {
        return getVolume() == 0;
    }

    public synchronized void mute() {
        if (isMute()) return;
        lastVol = getVolume();
        setVolume(0);
    }

    public synchronized void unmute() {
        if (!isMute()) return;
        setVolume(lastVol);
    }
}