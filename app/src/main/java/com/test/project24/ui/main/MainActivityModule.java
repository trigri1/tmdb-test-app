package com.test.project24.ui.main;

import android.arch.lifecycle.ViewModelProvider;

import com.test.project24.AppViewModelFactory;
import com.test.project24.data.IDataManager;
import com.test.project24.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gohar Ali on 17/01/2018.
 */
@Module
public class MainActivityModule {


    @Provides
    MainViewModel provideMainViewModel(IDataManager dataManager
            , SchedulerProvider schedulerProvider) {

        return new MainViewModel(dataManager, schedulerProvider);
    }


    @Provides
    ViewModelProvider.Factory mainViewModelProvider(MainViewModel mainViewModel) {
        return new AppViewModelFactory<>(mainViewModel);
    }


}
