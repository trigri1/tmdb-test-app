package com.test.project24.ui.base;


import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.test.project24.data.IDataManager;
import com.test.project24.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author goharali
 */

public class BaseViewModel<N> extends ViewModel {

    private N viewNavigator;
    private final IDataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;

    private ObservableBoolean isLoading = new ObservableBoolean(true);

    public BaseViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    public void setViewNavigator(N viewNavigator) {
        this.viewNavigator = viewNavigator;
    }

    public N getViewNavigator() {
        return viewNavigator;
    }

    public IDataManager getDataManager() {
        return dataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void addToCompositeDisposable(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }


    public ObservableBoolean getIsLoading() {
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
