package com.example.audiotest;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class MainActivityOld extends AppCompatActivity {
    private static final String TAG = MainActivityOld.class.getSimpleName();
    private static final int SEEK_TIME = 5000;   // 5sec
    private static final int UPDATE_TIME = 1000; // 1sec
    private ImageButton btnBackward;
    private ImageButton btnForward;
    private ImageButton btnPause;
    private ImageButton btnPlay;
    private TextView txtCurrentTime;
    private TextView txtTotalTime;
    private TextView txtSongName;
    private SeekBar seekBarSong;

    // for AudioTrack playback
    private Button btnStart;
    private Button btnStop;
    private RadioGroup radioGrp;
    private RadioButton radioBtnStatic;
    private RadioButton radioBtnStream;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    boolean mIsMaxSet = false;

    private BatteryManager mBatteryManager = null;

    // media player instance to playback the media file from the raw folder
    MediaPlayer mMediaPlayer;

    // Audio manager instance to manage or handle the audio interruptions
    //AudioManager audioManager;

    // Audio attributes instance to set the playback attributes for the media player instance
    // these attributes specify what type of media is to be played and used to callback the audioFocusChangeListener
    //AudioAttributes audioAttributes;

    // media player is handled according to the change in the focus which Android system grants for
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mMediaPlayer.release();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);
        btnBackward = findViewById(R.id.btnBackward);
        btnForward = findViewById(R.id.btnForward);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        txtCurrentTime = findViewById(R.id.txtCurTime);
        txtTotalTime = findViewById(R.id.txtTotalTime);
        txtSongName = findViewById(R.id.txtSname);
        seekBarSong = findViewById(R.id.sBar);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        radioGrp = findViewById(R.id.radioGrp);
        radioBtnStatic = findViewById(R.id.radioBtnStatic);
        radioBtnStream = findViewById(R.id.radioBtnStream);

        seekBarSong.setClickable(false);
        txtSongName.setText("music.mp3");

        mBatteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);

        mMediaPlayer = MediaPlayer.create(this, R.raw.music);

        btnPlay.setOnClickListener(v -> {

            // get the audio system service for the audioManger instance
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            // initiate the audio playback attributes
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            // set the playback attributes for the focus requester
            AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(audioAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(audioFocusChangeListener)
                    .build();

            // request the audio focus and store it in the int variable
            final int audioFocusRequest = audioManager.requestAudioFocus(focusRequest);

            if (audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMediaPlayer.start();
                updateAudioProgress();
                mHandler.postDelayed(updateSongTime, UPDATE_TIME);
            } else {
                System.out.println("audiofocus not granted");
            }
        });

        btnPause.setOnClickListener(v -> {
            mMediaPlayer.pause();
        });

        btnForward.setOnClickListener(v -> {
            int totalTime = mMediaPlayer.getDuration();
            int currentTime = mMediaPlayer.getCurrentPosition();
            if ((currentTime + SEEK_TIME) <= totalTime)
                mMediaPlayer.seekTo(currentTime + SEEK_TIME);

            if (!btnPlay.isEnabled())
                btnPlay.setEnabled(true);
        });

        btnBackward.setOnClickListener(v -> {
            int totalTime = mMediaPlayer.getDuration();
            int currentTime = mMediaPlayer.getCurrentPosition();
            if ((currentTime - SEEK_TIME) > 0)
                mMediaPlayer.seekTo(currentTime - SEEK_TIME);

            if (!btnPlay.isEnabled())
                btnPlay.setEnabled(true);
        });

        //////////////////////////////////////////////////////////////
        // AudioTrack button handlers
        btnStart.setOnClickListener(v -> {
            if (radioBtnStatic.isSelected()) {
                Runnable runnable = this::playAudioTrackStatic;
                Thread thread = new Thread(runnable);
                thread.start();
            } else {
                Runnable runnable = this::playAudioTrackStream;
                Thread thread = new Thread(runnable);
                thread.start();
            }

            if (btnStop.isEnabled())
                btnStop.setEnabled(false);
        });

        btnStop.setOnClickListener(v -> {
//            int totalTime = mMediaPlayer.getDuration();
//            int currentTime = mMediaPlayer.getCurrentPosition();
//            if ((currentTime - SEEK_TIME) > 0)
//                mMediaPlayer.seekTo(currentTime - SEEK_TIME);

            if (!btnPlay.isEnabled())
                btnPlay.setEnabled(true);
        });


        Button btnOffload = findViewById(R.id.btnOffload);
        btnOffload.setOnClickListener(v -> {
            Runnable runnable = this::playOffload;
            Thread thread = new Thread(runnable);
            thread.start();
        });

        Button btnWav = findViewById(R.id.btnWav);
        btnWav.setOnClickListener(v -> {
            Runnable runnable = this::playWav;
            Thread thread = new Thread(runnable);
            thread.start();
        });

        //new Thread(this::initSoundPool).start();


        setVolumeControlStream(AudioManager.STREAM_MUSIC);
//        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//
//        // Start listening for button presses
//        am.registerMediaButtonEventReceiver(RemoteControlReceiver);
//
//        // Stop listening for button presses
//        am.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "-- ON STOP --");
    }

    private final Runnable updateSongTime = new Runnable() {
        @Override
        public void run() {
            updateAudioProgress();
            mHandler.postDelayed(this, UPDATE_TIME);
        }
    };

    public void updateAudioProgress() {
        //get the audio duration
        int totalTime = mMediaPlayer.getDuration();
        int currentTime = mMediaPlayer.getCurrentPosition();

        if (!mIsMaxSet) {
            seekBarSong.setMax(totalTime);
            mIsMaxSet = true;
        }

        //display the audio duration
        txtTotalTime.setText(timeToString(totalTime));
        txtCurrentTime.setText(timeToString(currentTime));
        seekBarSong.setProgress(currentTime);

        Log.d(TAG, "Current Position: " + currentTime);

        int mBatteryCurrentNow = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        int mBatteryCurrentAvg = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);

        Log.d(TAG, "Battery current now: " + mBatteryCurrentNow);
        Log.d(TAG, "Battery current avg: " + mBatteryCurrentAvg);
    }

    //time conversion
    public String timeToString(long value) {
        String strTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0)
            strTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hrs, mns, scs);
        else
            strTime = String.format(Locale.getDefault(), "%02d:%02d", mns, scs);

        return strTime;
    }

    //AudioTrack functions
    public void playAudioTrackStream() {
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
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE);

        audioTrack.setVolume(0.1f);
        audioTrack.play();

        for (int i = 0; i < 100; i++) {
            audioTrack.write(buffer, 0, buffer.length);
            try {
                Thread.sleep(10000);
            } catch (Exception ignored) {
            }
        }

        audioTrack.stop();
        audioTrack.release();
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
        } catch (Exception ignored) {
        }

        audioTrack.stop();
        audioTrack.release();
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


    ////////////////////////////////////////////////////////////////////////
    SoundPool soundPool;
    int game_over, level_complete;

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
        game_over = soundPool.load(this, R.raw.music, 1);
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

    public void playSound(View v) {
        Log.d("------", "playSound, game_over : " + game_over);
        switch (v.getId()) {
            case R.id.btnSoundPool:
                // This play function takes five parameter leftVolume, rightVolume, priority, loop and rate.
                Log.d("------", "playSound, game_over : " + game_over);
                soundPool.play(game_over, 1, 1, 0, 0, 1);
                soundPool.autoPause();
                break;
            case R.id.btnSoundPool2:
                Log.d("------", "playSound, game_over : " + game_over);
                soundPool.play(level_complete, 1, 1, 0, 0, 1);
                break;
        }
    }
}