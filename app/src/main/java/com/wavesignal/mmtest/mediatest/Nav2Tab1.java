package com.wavesignal.mmtest.mediatest;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.wavesignal.mmtest.audiotest.R;

public class Nav2Tab1 extends Fragment {

    private static final String TAG = Nav2Tab1.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nav_2_tab_1, container, false);

        VideoView videoView = rootView.findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" +
                R.raw.videoplayback));
        videoView.start();

        return rootView;
    }
}
