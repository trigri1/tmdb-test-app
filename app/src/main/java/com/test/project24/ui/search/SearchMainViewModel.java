package com.test.project24.ui.search;

import com.test.project24.data.IDataManager;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.rx.SchedulerProvider;

public class SearchMainViewModel extends BaseViewModel<SearchMainNavigator> {
    public SearchMainViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
