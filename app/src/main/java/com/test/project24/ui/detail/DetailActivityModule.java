package com.test.project24.ui.detail;

import android.arch.lifecycle.ViewModelProvider;

import com.test.project24.AppViewModelFactory;
import com.test.project24.data.IDataManager;
import com.test.project24.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gohar Ali on 26/02/2018.
 */
@Module
public class DetailActivityModule {

    @Provides
    DetailViewModel provideDetailViewModel(IDataManager dataManager
            , SchedulerProvider schedulerProvider) {

        return new DetailViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory viewModelProviderFactory(DetailViewModel viewModel) {
        return new AppViewModelFactory<>(viewModel);
    }

}
