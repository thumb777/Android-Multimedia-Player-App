package com.example.audiotest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class TabFragment1 extends Fragment {

    static EditText textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_1, container, false);
        textView = (EditText) rootView.findViewById(R.id.textView);
        textView.setText("Scanning");
        return rootView;
    }
}
