package com.test.project24.ui.search;

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
public class SearchActivityModule {


    @Provides
    SearchMainViewModel provideSearchMainViewModel(IDataManager dataManager
            , SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        return new SearchMainViewModel(dataManager, schedulerProvider, compositeDisposable);
    }


    @Provides
    ViewModelProvider.Factory searchMainViewModelProvider(SearchMainViewModel mainViewModel) {
        return new AppViewModelFactory<>(mainViewModel);
    }


}
