package com.wavesignal.mmtest.audiotest;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Nav1Tab1 extends Fragment {

    private static final String TAG = Nav1Tab1.class.getSimpleName();

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

    boolean mIsMaxSet = false;

    private Handler mHandler = new Handler(Looper.getMainLooper());

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

    private final Runnable updateSongTime = new Runnable() {
        @Override
        public void run() {
            updateAudioProgress();
            mHandler.postDelayed(this, UPDATE_TIME);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nav_1_tab_1, container, false);

        btnBackward = rootView.findViewById(R.id.btnBackward);
        btnForward = rootView.findViewById(R.id.btnForward);
        btnPlay = rootView.findViewById(R.id.btnPlay);
        btnPause = rootView.findViewById(R.id.btnPause);

        txtCurrentTime = rootView.findViewById(R.id.txtCurTime);
        txtTotalTime = rootView.findViewById(R.id.txtTotalTime);
        txtSongName = rootView.findViewById(R.id.txtSname);
        seekBarSong = rootView.findViewById(R.id.sBar);

        seekBarSong.setClickable(false);
        txtSongName.setText("music.mp3");

        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.music);

        btnPlay.setOnClickListener(v -> {

            // get the audio system service for the audioManger instance
            AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

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

        return rootView;
    }

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
}
