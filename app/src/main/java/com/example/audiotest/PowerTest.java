package com.example.audiotest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class PowerTest extends AppCompatActivity {

    private static final String TAG = "BatteryCurrent";

    private static final int ONE_SEC_TIME = 1000; // in milliseconds
    private static final int UPDATE_TIME = 20; // in milliseconds
    private BatteryManager mBatteryManager = null;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private long mCurrentSum = 0;
    private long mCurrentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBatteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
        mHandler.postDelayed(updateBatteryStats, UPDATE_TIME);
    }

    private final Runnable updateBatteryStats = new Runnable() {
        @Override
        public void run() {
            updateBatteryCurrent();
            mHandler.postDelayed(this, UPDATE_TIME);
        }
    };

    int mPrintLog = 0;

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