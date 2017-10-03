package com.adsama.android.bakeup;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

import butterknife.BindView;

public class DetailActivity extends AppCompatActivity implements ExoPlayer.EventListener {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.exoPlayer)
    SimpleExoPlayerView mStepExoPlayerView;
    @BindView(R.id.tv_step_instruction)
    TextView mStepInstructionTextView;
    private SimpleExoPlayer mStepExoPlayer;
    private PlaybackStateCompat.Builder mStateBuilder;
    private MediaSessionCompat mMediaSession;
    private ArrayList<Steps> mStepsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        mStepsList = getIntent().getParcelableArrayListExtra("stepsData");
        int stepsPosition = getIntent().getIntExtra("stepsPosition", 0);
        initializeMediaSession();
        if (mStepsList.get(stepsPosition).getVideoURL() != null && !mStepsList.get(stepsPosition).getVideoURL().matches("")) {
            initializeMediaSession();
            initializeMediaPlayer(Uri.parse(mStepsList.get(stepsPosition).getVideoURL()));
        }
        Toast.makeText(this, "RECEIVED POSITION " + stepsPosition, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(this, LOG_TAG);
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

    private void initializeMediaPlayer(Uri videoUri) {
        if (mStepExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mStepExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mStepExoPlayerView.setPlayer(mStepExoPlayer);
            mStepExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(this, "BakeUp");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(this, userAgent), new DefaultExtractorsFactory(), null, null);
            mStepExoPlayer.prepare(mediaSource);
            mStepExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mStepExoPlayer != null) {
            mStepExoPlayer.stop();
            mStepExoPlayer.release();
            mStepExoPlayer = null;
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
