package com.wavesignal.mmtest.audiotest;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class TabFragment3 extends Fragment {

    // for AudioTrack playback
    private Button btnStart;
    private Button btnStop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_3, container, false);

        btnStart = rootView.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(view -> {
            Runnable runnable = this::playAudioTrackStatic;
            Thread thread = new Thread(runnable);
            thread.start();

            if (btnStop.isEnabled())
                btnStop.setEnabled(false);
        });

        btnStop = rootView.findViewById(R.id.btnStop);

        return rootView;
    }


    public void playAudioTrackStatic() {
        final int duration = 10; // duration of sound
        final int sampleRate = 44100; // Hz (maximum frequency is 7902.13Hz (B8))
        final int numSamples = duration * sampleRate;
        final double[] samples = new double[numSamples];
        final short[] buffer = new short[numSamples];
        for (int i = 0; i < numSamples; ++i) {
            samples[i] = Math.sin(2 * Math.PI * i * 440 / (sampleRate)); // Sine wave
            buffer[i] = (short) (samples[i] * Short.MAX_VALUE);  // Higher amplitude increases volume
        }

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        AudioFormat audioFormat = new AudioFormat.Builder()
                .setSampleRate(sampleRate)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO).build();

        AudioTrack audioTrack = new AudioTrack(
                audioAttributes,
                audioFormat,
                buffer.length,
                AudioTrack.MODE_STATIC,
                AudioManager.AUDIO_SESSION_ID_GENERATE);

        audioTrack.write(buffer, 0, buffer.length);
        audioTrack.play();

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        audioTrack.stop();
        audioTrack.release();
    }
}