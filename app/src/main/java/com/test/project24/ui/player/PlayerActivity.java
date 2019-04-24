package com.test.project24.ui.player;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.test.project24.BR;
import com.test.project24.R;
import com.test.project24.databinding.ActivityPlayerBinding;
import com.test.project24.ui.base.BaseActivity;
import com.test.project24.ui.player.player_controls.PlayerBottomControllerView;
import com.test.project24.ui.player.player_controls.media_controls.IPlayerControls;
import com.test.project24.ui.player.player_controls.player_events.EventLogger;
import com.test.project24.ui.player.player_controls.player_events.PlayerEventListener;
import com.test.project24.utils.Consts;
import com.test.project24.utils.DisplayScreen;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * @author goharali
 */

public class PlayerActivity extends BaseActivity<ActivityPlayerBinding, PlayerViewModel>
        implements HasActivityInjector, PlayerNavigator, IPlayerControls {

    private static final String TAG = PlayerActivity.class.getSimpleName();

    private static final String URL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";

    private static final int TIMEOUT = 30000;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory factory;


    private int movieModel;


    //Player Variables
    private SimpleExoPlayer player;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private DefaultTrackSelector trackSelector;
    private EventLogger eventLogger;

    private PlayerEventListener playerEventListener;
    //player variables
    PlayerBottomControllerView bottomView;

    private int currentWindow;
    private long playbackPosition;

    private boolean playWhenReady = true;
    private Boolean controlsVisible = false;

    private Handler playPauseHandler = new Handler();
    private Runnable playPauseRunnable;


    public static void toPlayerActivity(Context context, int movieId) {
        Intent i = new Intent(context, PlayerActivity.class);
        i.putExtra(Consts.EXTRA_MOVIE_ID, movieId);
        context.startActivity(i);
    }


    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initPlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            initPlayer();
        }

    }

    @Override
    public void onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        hideControls();
        super.onPause();
    }

    @Override
    public void onStop() {

        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
        super.onStop();
    }

    @Override
    public boolean useDefaultOrientation() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
        initActivity();
    }

    private void getExtras() {
        if (getIntent().hasExtra(Consts.EXTRA_MOVIE_ID)) {
            movieModel = getIntent().getIntExtra(Consts.EXTRA_MOVIE_ID, 0);
        }
    }

    @Override
    public void initActivity() {

        getViewDataBinding().inPlayer.setViewModel(getViewModel());
        getViewDataBinding().inPlayer.setNavigator(this);
    }

    @Override
    public PlayerViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(PlayerViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_player;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }


    /**
     * player initialization
     **/

    public void initPlayer() {


        boolean needNewPlayer = player == null;

        if (needNewPlayer) {

            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);


            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);

            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);

            setRootAnchors();

            //for player events
            player.addListener(playerEventListener);

            //for debugging
            //debugging logs
            eventLogger = new EventLogger(trackSelector);
            player.addListener(eventLogger);
            player.addMetadataOutput(eventLogger);
            player.setAudioDebugListener(eventLogger);
            player.setVideoDebugListener(eventLogger);

            getViewDataBinding().inPlayer.exoPlayer.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);

        }


        boolean haveResumePosition = currentWindow != C.INDEX_UNSET;
        if (haveResumePosition) {
            player.seekTo(currentWindow, playbackPosition);
        }

        MediaSource mediaSource = buildMediaSource(Uri.parse(URL));

        player.prepare(mediaSource, !haveResumePosition, false);
//        playerEventListener.setInErrorState(false);

        updateButtonVisibilities();


    }

    private MediaSource buildMediaSource(Uri uri) {

        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory("player", BANDWIDTH_METER
                        , TIMEOUT, TIMEOUT, false);
        return new ExtractorMediaSource(uri, dataSourceFactory,
                new DefaultExtractorsFactory(), null, null);
    }


    //attach controls to player
    private void setRootAnchors() {

        playerEventListener = new PlayerEventListener(this, getViewDataBinding().inPlayer.exoPlayer, this);

        bottomView = new PlayerBottomControllerView(this);

        bottomView.setMediaPlayer(this);

        bottomView.setAnchorView(getViewDataBinding().inPlayer.playerViewParent);

        hideControls();

    }


    private void releasePlayer() {
        if (player != null) {

            updateResumePosition();

            playWhenReady = player.getPlayWhenReady();
            player.removeListener(playerEventListener);

            player.release();
            player = null;
        }
    }


    public void showPausePlayMain() {


        if (playPauseHandler != null && playPauseRunnable != null)
            playPauseHandler.removeCallbacks(playPauseRunnable);

//        getViewDataBinding().inPlayer.sectionPlayPause.setVisibility(View.VISIBLE);

        getViewModel().setShowPlayPauseSection(true);

        playPauseRunnable = () -> {
            getViewModel().setShowPlayPauseSection(false);
//            getViewDataBinding().inPlayer.sectionPlayPause.setVisibility(View.GONE);
        };

        playPauseHandler.postDelayed(playPauseRunnable, 5000);
    }


    //player controls events
    @Override
    public void showPlayerLoader(boolean show) {
        getViewModel().setShowPlayerLoader(show);
    }

    @Override
    public void updateButtonVisibilities() {
    }

    @Override
    public void toggleControlsVisibility() {
        if (getControlsVisible()) {
            hideControls();
        } else {
            showControls();
        }
    }

    @Override
    public void toggleQualitySheet() {

    }

    @Override
    public void hideControls() {
        if (bottomView.isShowing()) {
            bottomView.hide();
        }
        getViewModel().setShowPlayPauseSection(false);
        setControlsVisible(false);
    }

    @Override
    public void showControls() {
        if (!bottomView.isShowing()) {
            bottomView.show();
            bottomView.bringToFront();
        }

        showPausePlayMain();
        setControlsVisible(true);
    }

    @Override
    public void setControlsVisible(Boolean visible) {
        this.controlsVisible = visible;
    }

    @Override
    public Boolean getControlsVisible() {
        return controlsVisible;
    }

    @Override
    public void hideShowPlayButton(Boolean isPlaying) {
        getViewModel().setShowMainPlayButton(!isPlaying);
    }

    @Override
    public void updateResumePosition() {
        currentWindow = player.getCurrentWindowIndex();
        playbackPosition = Math.max(0, player.getContentPosition());
    }

    @Override
    public void clearResumePosition() {
        currentWindow = C.INDEX_UNSET;
        playbackPosition = C.TIME_UNSET;
    }

    @Override
    public void start() {
        if (player != null) {
            player.setPlayWhenReady(true);
            bottomView.updatePausePlay();
        }
    }

    @Override
    public void pause() {
        if (player != null) {
            player.setPlayWhenReady(false);
            bottomView.updatePausePlay();
        }
    }

    @Override
    public int getDuration() {
        if (player != null)
            return (int) player.getDuration();
        else
            return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (player != null)
            return (int) player.getCurrentPosition();
        else
            return 0;
    }

    @Override
    public void seekTo(int pos) {
        if (player != null)
            player.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return player != null && player.getPlayWhenReady();
    }

    @Override
    public int getBufferPercentage() {
        if (player != null)
            return player.getBufferedPercentage();
        else
            return 0;
    }


    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public boolean isFullScreen() {
        int orientation = DisplayScreen.getScreenOrientation(this);
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            return false;
        } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            return true;
        }

        return false;
    }

    @Override
    public void toggleFullScreen() {

    }

    @Override
    public int getCurrentWindow() {
        return currentWindow;
    }

    @Override
    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    @Override
    public long getPlaybackPosition() {
        return playbackPosition;
    }

    @Override
    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    @Override
    public void onPlayerRootClicked(View view) {
        toggleControlsVisibility();
    }

    @Override
    public void onMainPlayClicked(View view) {
        start();
    }

    @Override
    public void onMainPauseClicked(View view) {
        pause();
    }


    @Override
    public void onBackPressed() {

        bottomView.removeAllViews();
        if (playPauseHandler != null && playPauseRunnable != null)
            playPauseHandler.removeCallbacks(playPauseRunnable);

        releasePlayer();

        super.onBackPressed();
    }


}
