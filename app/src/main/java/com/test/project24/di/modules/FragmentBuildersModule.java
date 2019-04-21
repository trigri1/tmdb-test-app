package com.test.project24.di.modules;


import com.test.project24.ui.main.changes_frag.ChangesFragment;
import com.test.project24.ui.main.changes_frag.ChangesFragmentModule;
import com.test.project24.ui.main.search_frag.SearchFragment;
import com.test.project24.ui.main.search_frag.SearchFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = {SearchFragmentModule.class, CommonInjectionModule.class})
    abstract SearchFragment bindSearchFragment();

    @ContributesAndroidInjector(modules = {ChangesFragmentModule.class, CommonInjectionModule.class})
    abstract ChangesFragment bindChangesFragment();

}
