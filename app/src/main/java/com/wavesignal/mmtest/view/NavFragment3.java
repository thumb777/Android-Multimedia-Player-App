package com.wavesignal.mmtest.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wavesignal.mmtest.audiotest.R;

public class NavFragment3 extends Fragment {
    public static NavFragment3 newInstance() {
        NavFragment3 fragment = new NavFragment3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_fragment_3, container, false);
    }
}