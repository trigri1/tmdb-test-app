package com.test.project24.ui.detail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.test.project24.BR;
import com.test.project24.R;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.databinding.ActivityDetailBinding;
import com.test.project24.ui.base.BaseActivity;
import com.test.project24.ui.player.PlayerActivity;
import com.test.project24.utils.Consts;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Gohar Ali on 26/02/2018.
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
        initActivity();
    }


    private void getExtras() {
        if (getIntent().hasExtra(Consts.EXTRA_MOVIE_ID)) {
            movieId = getIntent().getIntExtra(Consts.EXTRA_MOVIE_ID, 0);
        }
    }

    @Override
    public void initActivity() {
        getViewModel().setViewNavigator(this);
        getViewModel().getMovieDetail(movieId);
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
    public void onPlayClicked(View view, MovieDetail movieDetail) {
        PlayerActivity.toPlayerActivity(this, movieDetail.getId());
    }
}
