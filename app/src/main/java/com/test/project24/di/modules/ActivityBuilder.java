package com.test.project24.di.modules;


import com.test.project24.ui.detail.DetailActivity;
import com.test.project24.ui.detail.DetailActivityModule;
import com.test.project24.ui.main.MainActivity;
import com.test.project24.ui.main.MainActivityModule;
import com.test.project24.ui.player.PlayerActivity;
import com.test.project24.ui.player.PlayerActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Gohar Ali on 28/11/2017.
 */
@Module
public abstract class ActivityBuilder {

//    @Binds
//    @IntoMap
//    @ActivityKey(SplashActivity.class)
//    abstract AndroidInjector.Factory<? extends AppCompatActivity> bindSplashActivity(SplashActivity splashActivity);

//    @ContributesAndroidInjector(modules = {SplashActivityModule.class, CommonInjectionModule.class})
//    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class, CommonInjectionModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {DetailActivityModule.class, CommonInjectionModule.class})
    abstract DetailActivity bindDetaileActivity();

    @ContributesAndroidInjector(modules = {PlayerActivityModule.class, CommonInjectionModule.class})
    abstract PlayerActivity bindPlayerActivity();

}