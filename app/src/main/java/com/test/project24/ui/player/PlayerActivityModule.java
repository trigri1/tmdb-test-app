package com.test.project24.ui.player;

import android.arch.lifecycle.ViewModelProvider;

import com.test.project24.AppViewModelFactory;
import com.test.project24.data.IDataManager;
import com.test.project24.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayerActivityModule {

    @Provides
    PlayerViewModel proviPlayerViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        return new PlayerViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory provideFactory(PlayerViewModel playerViewModel) {
        return new AppViewModelFactory<>(playerViewModel);
    }
}
