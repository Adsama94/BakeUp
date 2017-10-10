package com.adsama.android.bakeup;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsama.android.bakeup.Model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class DetailFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final String BUNDLE_KEY = "bundle_key";
    private static final String BUNDLE_POSITION = "bundle_position";
    private static final String PLAYER_POSITION = "player_position";
    private SimpleExoPlayerView mStepExoPlayerView;
    private SimpleExoPlayer mStepExoPlayer;
    private PlaybackStateCompat.Builder mStateBuilder;
    private TextView mStepInstructionTextView;
    private MediaSessionCompat mMediaSession;
    private ArrayList<Steps> mStepsList;
    private ImageView mEmptyVideoView;
    private int stepsPosition;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mStepsList = getArguments().getParcelableArrayList(BUNDLE_KEY);
        stepsPosition = getArguments().getInt(BUNDLE_POSITION);
        mEmptyVideoView = rootView.findViewById(R.id.iv_empty_video);
        mStepExoPlayerView = rootView.findViewById(R.id.exoPlayer);
        mStepInstructionTextView = rootView.findViewById(R.id.tv_step_instruction);
        mStepInstructionTextView.setTypeface(EasyFonts.droidSerifBold(getContext()));
        if (savedInstanceState != null) {
            savedInstanceState.getLong(PLAYER_POSITION);
            Long actualPosition = Long.valueOf(PLAYER_POSITION);
            Log.e(LOG_TAG, "POSITION IS " + actualPosition);
            mStepExoPlayer.seekTo(actualPosition);
            initializeMediaSession();
        } else {
            initializeMediaSession();
        }
        if (mStepsList.get(stepsPosition).getVideoURL() != null && !mStepsList.get(stepsPosition).getVideoURL().matches("")) {
            initializeMediaSession();
            initializeMediaPlayer(Uri.parse(mStepsList.get(stepsPosition).getVideoURL()));
            mStepInstructionTextView.setText(String.valueOf(mStepsList.get(stepsPosition).getDescription()));
        } else {
            mStepExoPlayerView.setVisibility(View.GONE);
            mEmptyVideoView.setVisibility(View.VISIBLE);
            mStepInstructionTextView.setText(String.valueOf(mStepsList.get(stepsPosition).getDescription()));
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mStepExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        outState.putLong(PLAYER_POSITION, mStepExoPlayer.getCurrentPosition());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getContext(), LOG_TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PAUSE |
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    private void releasePlayer() {
        if (mStepExoPlayer != null) {
            mStepExoPlayer.stop();
            mStepExoPlayer.release();
            mStepExoPlayer = null;
        }
    }

    private void initializeMediaPlayer(Uri videoUri) {
        if (mStepExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mStepExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mStepExoPlayerView.setPlayer(mStepExoPlayer);
            mStepExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "BakeUp");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mStepExoPlayer.prepare(mediaSource);
            mStepExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mStepExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mStepExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mStepExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mStepExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mStepExoPlayer.seekTo(0);
        }
    }
}