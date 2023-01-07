package com.wavesignal.mmtest.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wavesignal.mmtest.ControlReceiver;
import com.wavesignal.mmtest.audiotest.R;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)
                bottomNavigationView.getChildAt(0);
        try {
//            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//
//            shiftingMode.setAccessible(true);
//            shiftingMode.setBoolean(menuView, false);
//            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {

                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //item.setShiftingMode(false);
                //To update view, set the checked value again
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        BottomNavigationView.OnItemSelectedListener mOnItemSelectedListener
                = item -> {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.action_item1:
                    selectedFragment = NavFragment1.newInstance();
                    break;
                case R.id.action_item2:
                    selectedFragment = NavFragment2.newInstance();
                    break;
                case R.id.action_item3:
                    selectedFragment = NavFragment3.newInstance();
                    break;
                case R.id.action_item4:
                    selectedFragment = NavFragment4.newInstance();
                    break;
                case R.id.action_item5:
                    selectedFragment = NavFragment5.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        };
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, NavFragment1.newInstance());
        transaction.commit();

        bottomNavigationView.setOnItemSelectedListener(mOnItemSelectedListener);

//        HomeScreenPresenter.getInstance().test();
//
//        File pcmFile;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            pcmFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + Const.FS + "recording.txt");
//        } else {
//            pcmFile = new File(Environment.getExternalStorageDirectory(), "recording.txt");
//        }
//        Log.d("MainActivity", "File===========> " + pcmFile.getAbsolutePath());
//        try {
//            String str = "Vilas";
//            byte[] utf = str.getBytes(StandardCharsets.UTF_8);
//            FileOutputStream outStream = new FileOutputStream(pcmFile);
//            outStream.write(utf, 0, utf.length);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    ControlReceiver controlReceiver = new ControlReceiver();

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(controlReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(controlReceiver);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.action_bar_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }
}