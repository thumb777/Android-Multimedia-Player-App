package com.wavesignal.mmtest.audiotest;

import android.media.AudioAttributes;
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

public class TabFragment5 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_5, container, false);

        Button btnWav = rootView.findViewById(R.id.btnWav);
        btnWav.setOnClickListener(v -> {
            Runnable runnable = this::playWav;
            Thread thread = new Thread(runnable);
            thread.start();
        });

        return rootView;
    }

    public void playWav() {
        Log.d("------", "play");

        InputStream is = getResources().openRawResource(R.raw.sinewaves);

        int minBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        AudioFormat audioFormat = new AudioFormat.Builder()
                .setSampleRate(44100)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO).build();

        AudioTrack audioTrack = new AudioTrack(
                audioAttributes,
                audioFormat,
                minBufferSize,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE);

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