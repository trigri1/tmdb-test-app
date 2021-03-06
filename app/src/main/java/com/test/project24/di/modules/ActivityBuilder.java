package com.test.project24.di.modules;


import com.test.project24.ui.detail.DetailActivity;
import com.test.project24.ui.detail.DetailActivityModule;
import com.test.project24.ui.main.MainActivity;
import com.test.project24.ui.main.MainActivityModule;
import com.test.project24.ui.player.PlayerActivity;
import com.test.project24.ui.player.PlayerActivityModule;
import com.test.project24.ui.search.SearchActivity;
import com.test.project24.ui.search.SearchActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Gohar Ali
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class, CommonInjectionModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {SearchActivityModule.class, CommonInjectionModule.class})
    abstract SearchActivity bindSearchActivity();

    @ContributesAndroidInjector(modules = {DetailActivityModule.class, CommonInjectionModule.class})
    abstract DetailActivity bindDetaileActivity();

    @ContributesAndroidInjector(modules = {PlayerActivityModule.class, CommonInjectionModule.class})
    abstract PlayerActivity bindPlayerActivity();

}