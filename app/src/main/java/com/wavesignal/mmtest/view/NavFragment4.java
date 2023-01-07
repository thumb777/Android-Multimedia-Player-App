package com.wavesignal.mmtest.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wavesignal.mmtest.audiotest.R;

public class NavFragment4 extends Fragment {
    public static NavFragment4 newInstance() {
        NavFragment4 fragment = new NavFragment4();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_fragment_4, container, false);
    }
}
