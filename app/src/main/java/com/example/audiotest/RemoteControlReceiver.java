package com.example.audiotest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

public class RemoteControlReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("_____", "button is pressed");
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
                Log.d("_____", "Media play is pressed");
            } else if (KeyEvent.KEYCODE_VOLUME_DOWN == event.getKeyCode()) {
                Log.d("_____", "volume down is pressed");
            } else if (KeyEvent.KEYCODE_VOLUME_DOWN == event.getKeyCode()) {
                Log.d("_____", "volume up is pressed");
            }
        }
    }
}
