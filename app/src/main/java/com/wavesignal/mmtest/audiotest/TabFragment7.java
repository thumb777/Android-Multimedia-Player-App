package com.wavesignal.mmtest.audiotest;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wavesignal.mmtest.ControlReceiver;
import com.wavesignal.mmtest.SettingsObserver;

public class TabFragment7 extends Fragment {

    private static final String TAG = TabFragment7.class.getSimpleName();

    private static final int ONE_SEC_TIME = 1000; // in milliseconds
    private static final int UPDATE_TIME = 20; // in milliseconds
    private BatteryManager mBatteryManager = null;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private long mCurrentSum = 0;
    private long mCurrentCount = 0;

    private int mPrintLog = 0;

    SettingsObserver settingsContentObserver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_7, container, false);

        //getContext().getApplicationContext().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        settingsContentObserver = new SettingsObserver(getContext(), new Handler());

        getContext().getContentResolver().registerContentObserver(android.provider.Settings.System.
                CONTENT_URI, true, settingsContentObserver);

        mBatteryManager = (BatteryManager) getContext().getSystemService(Context.BATTERY_SERVICE);
        mHandler.postDelayed(updateBatteryStats, UPDATE_TIME);

        return rootView;
    }

    @Override
    public void onDestroy() {
        getContext().getContentResolver().unregisterContentObserver(settingsContentObserver);
        super.onDestroy();
    }

    private final Runnable updateBatteryStats = new Runnable() {
        @Override
        public void run() {
            updateBatteryCurrent();
            mHandler.postDelayed(this, UPDATE_TIME);
        }
    };

    public void updateBatteryCurrent() {
        int currentNow = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        //int currentAvg = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);

        mCurrentCount++;
        mCurrentSum = mCurrentSum + currentNow;
        long currentAvg = mCurrentSum / mCurrentCount;

        mPrintLog = mPrintLog + UPDATE_TIME;
        if (mPrintLog == ONE_SEC_TIME) {
            mPrintLog = 0;
            Log.d(TAG, "Battery current, now: " + currentNow + ", avg: " + currentAvg);
        }
    }
}