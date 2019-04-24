package com.test.project24.ui.search.search_frag;

import android.arch.lifecycle.ViewModelProvider;

import com.test.project24.AppViewModelFactory;
import com.test.project24.data.IDataManager;
import com.test.project24.ui.search.search_frag.adapter.SearchAdapter;
import com.test.project24.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class SearchFragmentModule {

    @Provides
    SearchViewModel provideHorizontalViewModel(IDataManager dataManager,
                                               SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        return new SearchViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    SearchAdapter provideHorizontalAdapter() {
        return new SearchAdapter();
    }

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(SearchViewModel searchViewModel) {
        return new AppViewModelFactory<>(searchViewModel);
    }

}
