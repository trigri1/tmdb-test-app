package com.test.project24.ui.main;

import android.arch.lifecycle.ViewModelProvider;

import com.test.project24.AppViewModelFactory;
import com.test.project24.data.IDataManager;
import com.test.project24.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Gohar Ali
 */
@Module
public class MainActivityModule {
    @Provides
    MainViewModel provideMainViewModel(IDataManager dataManager
            , SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        return new MainViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(MainViewModel mainViewModel) {
        return new AppViewModelFactory<>(mainViewModel);
    }

}
