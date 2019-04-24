package com.test.project24.ui.detail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.test.project24.BR;
import com.test.project24.R;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.databinding.ActivityDetailBinding;
import com.test.project24.ui.base.BaseActivity;
import com.test.project24.ui.player.PlayerActivity;
import com.test.project24.utils.CommonUtils;
import com.test.project24.utils.Consts;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * @author goharali
 */

public class DetailActivity extends BaseActivity<ActivityDetailBinding, DetailViewModel>
        implements HasActivityInjector, DetailNavigator {


    private static final String TAG = DetailActivity.class.getSimpleName();
    private int movieId;

    public static void toDetailActivity(Context context, int movieId) {
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra(Consts.EXTRA_MOVIE_ID, movieId);
        context.startActivity(i);
    }


    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;


    @Inject
    ViewModelProvider.Factory factory;


    @Override
    public boolean useDefaultOrientation() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
        initActivity();
    }


    private void getExtras() {
        if (getIntent().hasExtra(Consts.EXTRA_MOVIE_ID)) {
            movieId = getIntent().getIntExtra(Consts.EXTRA_MOVIE_ID, -1);
        }
    }

    @Override
    public void initActivity() {
        setSupportActionBar(getViewDataBinding().toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getViewModel().setViewNavigator(this);
        getViewModel().getMovieDetail(movieId);

        getViewDataBinding().toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public DetailViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(DetailViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onPlayClicked(MovieDetail movieDetail) {
        PlayerActivity.toPlayerActivity(this, movieDetail.getId());
    }

    @Override
    public void onErrorReceived(String message) {
        if (!isFinishing()) {
            CommonUtils.showToast(getApplicationContext(), message);
        }
    }

}
