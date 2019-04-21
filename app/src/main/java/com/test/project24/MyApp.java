package com.test.project24;

import android.app.Activity;
import android.app.Application;


import com.squareup.leakcanary.LeakCanary;
import com.test.project24.di.AppInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

//    @Inject
//    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        AppInjector.init(this);
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

//    @Override
//    public AndroidInjector<Fragment> supportFragmentInjector() {
//        return fragmentDispatchingAndroidInjector;
//    }
}
