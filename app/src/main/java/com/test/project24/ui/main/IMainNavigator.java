package com.test.project24.ui.main;

import com.test.project24.data.network.models.MovieSearchModel;

public interface IMainNavigator {

    void loadData(MovieSearchModel homeDataModel);

    void addNavigationListeners();
}
