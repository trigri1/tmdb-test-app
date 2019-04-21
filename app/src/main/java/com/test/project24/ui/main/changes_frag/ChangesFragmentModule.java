package com.test.project24.ui.main.changes_frag;

import android.arch.lifecycle.ViewModelProvider;

import com.test.project24.AppViewModelFactory;
import com.test.project24.data.IDataManager;
import com.test.project24.ui.main.changes_frag.adapter.ChangesAdapter;
import com.test.project24.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ChangesFragmentModule {

    @Provides
    ChangesViewModel provideChangesViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ChangesViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ChangesAdapter provideChangesAdapter() {
        return new ChangesAdapter();
    }

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(ChangesViewModel changesViewModel) {
        return new AppViewModelFactory<>(changesViewModel);
    }

}
