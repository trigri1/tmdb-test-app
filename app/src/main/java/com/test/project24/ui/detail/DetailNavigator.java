package com.test.project24.ui.detail;

import com.test.project24.data.network.models.detail.MovieDetail;

public interface DetailNavigator {
    void onPlayClicked(MovieDetail movieDetail);

    void onErrorReceived(String message);
}
