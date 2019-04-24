package com.test.project24.ui.main;

import com.test.project24.data.IDataManager;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author goharali
 */

public class MainViewModel extends BaseViewModel<IMainNavigator> {

    public MainViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

}
