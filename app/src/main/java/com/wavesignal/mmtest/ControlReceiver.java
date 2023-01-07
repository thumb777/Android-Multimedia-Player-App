package com.wavesignal.mmtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class ControlReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            if (isAirplaneModeOn(context.getApplicationContext())) {
                Toast.makeText(context, "AirPlane mode is on", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "AirPlane mode is off", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}
