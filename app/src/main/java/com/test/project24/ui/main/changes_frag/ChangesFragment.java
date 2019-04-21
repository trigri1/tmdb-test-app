package com.test.project24.ui.main.changes_frag;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.test.project24.BR;
import com.test.project24.R;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.databinding.FragmentChangesBinding;
import com.test.project24.ui.base.BaseFragment;
import com.test.project24.ui.main.changes_frag.adapter.ChangesAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ChangesFragment extends BaseFragment<FragmentChangesBinding, ChangesViewModel>
        implements HasSupportFragmentInjector, ChangesNavigator {


    private static String SELECTED_LANG = "en";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    ChangesAdapter adapter;


    public static ChangesFragment newInstance() {

        Bundle args = new Bundle();
        ChangesFragment fragment = new ChangesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        getViewModel().setViewNavigator(this);
        getViewModel().getChangesList(SELECTED_LANG, "", "", 1);
    }

    @Override
    public ChangesViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ChangesViewModel.class);
    }

    private void setAdapter() {
        getViewDataBinding().rvChanges.setAdapter(adapter);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_changes;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onChangesReceived(List<MovieDetail> list) {
        if (!list.isEmpty()) {
            getViewModel().getMovieDetail(list.get(0).getId());
        }
    }

    @Override
    public void onRetryClicked() {
        getViewModel().getChangesList(SELECTED_LANG, "", "", 1);
    }
}
