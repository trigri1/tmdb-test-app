package com.test.project24.ui.player.player_controls.player_events;

import android.content.Context;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.test.project24.R;
import com.test.project24.ui.player.player_controls.media_controls.IPlayerControls;
import com.test.project24.utils.CommonUtils;

public class PlayerEventListener implements Player.EventListener {

    private Boolean isPlayerReady = false;
    private SimpleExoPlayerView playerView;
    private Context context;
    private IPlayerControls playerControls;
    private boolean inErrorState = false;
    private boolean isEnded = false;

    public PlayerEventListener(Context context, SimpleExoPlayerView playerView, IPlayerControls playerControls) {

        this.context = context;
        this.playerView = playerView;
        this.playerControls = playerControls;
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
        String stateString;
        switch (playbackState) {
            case Player.STATE_IDLE:
                playerView.hideController();
                stateString = "ExoPlayer.STATE_IDLE      -";
                break;
            case Player.STATE_BUFFERING:
                isEnded = false;
                playerControls.showPlayerLoader(true);
                playerControls.hideControls();
                //                circularLoader.setVisibility(View.VISIBLE);
                stateString = "ExoPlayer.STATE_BUFFERING -";
                break;
            case Player.STATE_READY:
                stateString = "ExoPlayer.STATE_READY     -";
                onPlayerReady();

                break;
            case Player.STATE_ENDED:
                stateString = "ExoPlayer.STATE_ENDED     -";
                playerControls.pause();
                playerControls.seekTo(0);
                isEnded = true;
                break;
            default:
                stateString = "UNKNOWN_STATE             -";
                break;
        }
        Log.e("onPlayerStateChanged", "changed state to " + stateString + " playWhenReady: " + playWhenReady);

        playerControls.updateButtonVisibilities();

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        String errorString = null;
        if (error.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = error.getRendererException();
            if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                        (MediaCodecRenderer.DecoderInitializationException) cause;
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                        errorString = context.getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString =
                                context.getString(R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                    } else {
                        errorString = context.getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                    }
                } else {
                    errorString = context.getString(R.string.error_instantiating_decoder,
                                                    decoderInitializationException.decoderName);
                }
            }
        }
        if (errorString != null) {
            CommonUtils.showToast(context, errorString);
        }
        inErrorState = true;
        if (isBehindLiveWindow(error)) {
            playerControls.clearResumePosition();
        } else {
            playerControls.updateResumePosition();
            playerControls.updateButtonVisibilities();
        }
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    private boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private void onPlayerReady() {
        playerControls.showPlayerLoader(false);

        if (!isPlayerReady) {
            playerControls.toggleControlsVisibility();
        }

        isPlayerReady = true;
    }
}
