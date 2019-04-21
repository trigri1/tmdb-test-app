package com.test.project24;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by Gohar Ali on 16/01/2018.
 */

public class AppViewModelFactory<V> implements ViewModelProvider.Factory {


    private V viewModel;

    public AppViewModelFactory(V viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModel.getClass())) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
