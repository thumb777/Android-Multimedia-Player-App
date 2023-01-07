package com.wavesignal.mmtest;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.widget.Toast;

import java.util.Objects;

public class SettingsObserver extends ContentObserver {

    int previousVolume;
    Context context;

    public SettingsObserver(Context c, Handler handler) {
        super(handler);
        context = c;
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume =
                Objects.requireNonNull(audio).getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume =
                Objects.requireNonNull(audio).getStreamVolume(AudioManager.STREAM_MUSIC);
        int delta = previousVolume - currentVolume;
        if (delta > 0) {
            System.out.println("Volume Decreased");
            Toast.makeText(context.getApplicationContext(), "Volume Decreased", Toast.LENGTH_SHORT).show();
            previousVolume = currentVolume;
        } else if (delta < 0) {
            System.out.println("Volume Increased");
            Toast.makeText(context.getApplicationContext(), "Volume Increased", Toast.LENGTH_SHORT).show();
            previousVolume = currentVolume;
        }
    }
}
