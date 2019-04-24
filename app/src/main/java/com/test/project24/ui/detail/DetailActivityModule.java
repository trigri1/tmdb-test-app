package com.test.project24.ui.detail;

import android.arch.lifecycle.ViewModelProvider;

import com.test.project24.AppViewModelFactory;
import com.test.project24.data.IDataManager;
import com.test.project24.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author goharali
 */
@Module
public class DetailActivityModule {

    @Provides
    DetailViewModel provideDetailViewModel(IDataManager dataManager
            , SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {

        return new DetailViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    ViewModelProvider.Factory viewModelProviderFactory(DetailViewModel viewModel) {
        return new AppViewModelFactory<>(viewModel);
    }

}
