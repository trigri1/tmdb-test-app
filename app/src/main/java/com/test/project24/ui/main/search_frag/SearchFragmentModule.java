package com.test.project24.ui.main.search_frag;

import android.arch.lifecycle.ViewModelProvider;

import com.test.project24.AppViewModelFactory;
import com.test.project24.data.IDataManager;
import com.test.project24.ui.main.search_frag.adapter.SearchAdapter;
import com.test.project24.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchFragmentModule {

    @Provides
    SearchViewModel provideHorizontalViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        return new SearchViewModel(dataManager, schedulerProvider);
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
