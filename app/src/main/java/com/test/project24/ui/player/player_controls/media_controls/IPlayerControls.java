package com.test.project24.ui.player.player_controls.media_controls;


public interface IPlayerControls {


    void showPlayerLoader(boolean show);


    void updateButtonVisibilities();

    void toggleControlsVisibility();

    void toggleQualitySheet();

    void hideControls();

    void showControls();

    void setControlsVisible(Boolean visible);

    Boolean getControlsVisible();

    void hideShowPlayButton(Boolean isPlaying);

    void updateResumePosition();

    void clearResumePosition();


    //    Player Media controls
    void start();

    void pause();

    int getDuration();

    int getCurrentPosition();

    void seekTo(int pos);

    boolean isPlaying();

    int getBufferPercentage();

    boolean canPause();

    boolean canSeekBackward();

    boolean canSeekForward();

    boolean isFullScreen();

    void toggleFullScreen();

    int getCurrentWindow();

    void setCurrentWindow(int currentWindow);

    long getPlaybackPosition();

    void setPlaybackPosition(long playbackPosition);
}
