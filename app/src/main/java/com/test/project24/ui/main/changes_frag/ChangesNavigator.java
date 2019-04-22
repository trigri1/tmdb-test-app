package com.test.project24.ui.main.changes_frag;

import com.test.project24.data.network.models.detail.MovieDetail;

import java.util.List;

public interface ChangesNavigator {
    void onChangesReceived(List<MovieDetail> list);

    void onStartDateClicked();

    void onEndDateClicked();

    void onFetchClicked();

    void onRetryClicked();

    void onErrorReceived(String message);
}
