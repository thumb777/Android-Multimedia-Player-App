package com.wavesignal.mmtest.audiotest;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;

public class Nav1Tab4 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nav_1_tab_4, container, false);

        Button btnOffload = rootView.findViewById(R.id.btnOffload);
        btnOffload.setOnClickListener(v -> {
            Runnable runnable = this::playOffload;
            Thread thread = new Thread(runnable);
            thread.start();
        });

        return rootView;
    }

    public void playOffload() {
        Log.d("------", "play");

        InputStream is = getResources().openRawResource(R.raw.music);

        int minBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_MP3);

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_MP3, minBufferSize, AudioTrack.MODE_STREAM);

        try {
            int i = 0;
            byte[] buffer = new byte[512];
            audioTrack.play();

            Log.d("------", "write starts");
            while ((i = is.read(buffer)) != -1) {
                audioTrack.write(buffer, 0, i);
            }
            Log.d("------", "write ends");
        } catch (IOException e) {
            e.printStackTrace();
        }

        audioTrack.stop();
        audioTrack.release();
    }
}