package com.wavesignal.mmtest.audiotest;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;

public class TabFragment6 extends Fragment {

    private SoundPool soundPool;
    private int game_over, level_complete;

    private Button btnSoundpool1;
    private Button btnSoundpool2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.tab_fragment_6, container, false);

        btnSoundpool1 = rooView.findViewById(R.id.btnSoundPool);
        btnSoundpool1.setOnClickListener(view -> {
            initSoundPool();

            // This play function takes five parameter leftVolume, rightVolume, priority, loop and rate.
            Log.d("------", "playSound, game_over : " + game_over);
            soundPool.play(game_over, 1, 1, 0, 0, 1);
            soundPool.autoPause();
        });

        btnSoundpool2 = rooView.findViewById(R.id.btnSoundPool2);
        btnSoundpool2.setOnClickListener(view -> {
            initSoundPool();

            Log.d("------", "playSound, game_over : " + game_over);
            soundPool.play(level_complete, 1, 1, 0, 0, 1);
        });

        //new Thread(this::initSoundPool).start();

        return rooView;
    }


    public void initSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();

        // This load function takes three parameter context, file_name and priority.
        game_over = soundPool.load(getContext(), R.raw.music, 1);
        //level_complete = soundPool.load(this, R.raw.sinewaves, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.d("------", "onLoadComplete, sampleId : " + sampleId);
                soundPool.play(sampleId, 1.0f, 1.0f, 0, 0, 1.0f);
            }
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}