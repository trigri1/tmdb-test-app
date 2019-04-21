package com.test.project24.ui.detail;

import android.view.View;

import com.test.project24.data.network.models.detail.MovieDetail;

public interface DetailNavigator {
    void onPlayClicked(View view, MovieDetail movieDetail);
}
