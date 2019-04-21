package com.test.project24.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.test.project24.MyApp;
import com.test.project24.di.components.ApplicationComponent;
import com.test.project24.di.components.DaggerApplicationComponent;

import dagger.android.AndroidInjection;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Gohar Ali on 28/11/2017.
 */

public class AppInjector {

    private AppInjector() {
    }

    public static ApplicationComponent init(MyApp myApp) {

        /** New implementation of {@link ApplicationComponent} with the use of
         * {@link dagger.Component.Builder} rather than use of constructor of
         * {@link com.khaleef.one23fun.di.modules.ApplicationModul} to provide application dependency
         * */

        ApplicationComponent applicationComponent = DaggerApplicationComponent
                .builder()
                .application(myApp)
                .build();

        applicationComponent.inject(myApp);

        myApp.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                if (activity instanceof HasActivityInjector
                        || activity instanceof HasSupportFragmentInjector) {
                    AndroidInjection.inject(activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

        return applicationComponent;
    }
}
