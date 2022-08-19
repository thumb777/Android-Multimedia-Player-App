package com.example.audiotest;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class AudioTrackPlayer {
    Context mContext;
    int minBufferSize;
    AudioTrack audioTrack;
    boolean STOPPED;

    public AudioTrackPlayer(Context context) {
        mContext = context;
    }

    public void play() {
        Log.d("------", "play");

        InputStream is = mContext.getResources().openRawResource(R.raw.music);

        minBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        AudioFormat audioFormat = new AudioFormat.Builder()
                .setSampleRate(44100)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO).build();

//        audioTrack = new AudioTrack(
//                audioAttributes,
//                audioFormat,
//                minBufferSize,
//                AudioTrack.MODE_STREAM,
//                AudioManager.AUDIO_SESSION_ID_GENERATE);

        audioTrack= new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO,
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