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

    Fragment activeFragment;
    Fragment selectedFragment1 = NavFragment1.newInstance();
    Fragment selectedFragment2 = NavFragment2.newInstance();
    Fragment selectedFragment3 = NavFragment3.newInstance();
    Fragment selectedFragment4 = NavFragment4.newInstance();
    Fragment selectedFragment5 = NavFragment5.newInstance();

    ControlReceiver controlReceiver = new ControlReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(mOnItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.add(R.id.frame_layout, selectedFragment1);
        transaction.add(R.id.frame_layout, selectedFragment2).hide(selectedFragment2);
        transaction.add(R.id.frame_layout, selectedFragment3).hide(selectedFragment3);
        transaction.add(R.id.frame_layout, selectedFragment4).hide(selectedFragment4);
        transaction.add(R.id.frame_layout, selectedFragment5).hide(selectedFragment5);
        transaction.commit();

        activeFragment =  selectedFragment1;
    }

    BottomNavigationView.OnItemSelectedListener mOnItemSelectedListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.action_item1:
                selectedFragment = selectedFragment1;
                break;
            case R.id.action_item2:
                selectedFragment = selectedFragment2;
                break;
            case R.id.action_item3:
                selectedFragment = selectedFragment3;
                break;
            case R.id.action_item4:
                selectedFragment = selectedFragment4;
                break;
            case R.id.action_item5:
                selectedFragment = selectedFragment5;
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.hide(activeFragment).show(selectedFragment);
        transaction.commit();

        activeFragment = selectedFragment;

        return true;
    };

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