package com.test.project24.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.test.project24.BR;
import com.test.project24.R;
import com.test.project24.databinding.ActivityMainBinding;
import com.test.project24.ui.base.BaseActivity;
import com.test.project24.ui.main.changes_frag.ChangesFragment;
import com.test.project24.ui.search.SearchActivity;
import com.test.project24.utils.Consts;
import com.test.project24.utils.locale.AppLocale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements HasSupportFragmentInjector {

    private static String SELECTED_LANG = "en";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory appViewModelFactory;

    private SearchView searchView;

    @Override
    public boolean useDefaultOrientation() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        addFragment();
    }

    @Override
    public void initActivity() {
        SELECTED_LANG = AppLocale.getLang(this);
    }

    private void addFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, ChangesFragment.newInstance())
                .commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    @Override
    public MainViewModel getViewModel() {
        return ViewModelProviders.of(this, appViewModelFactory).get(MainViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_language) {

            if (AppLocale.getLang(this).equals(Consts.LANG_ENG)) {
                AppLocale.putLang(this, Consts.LANG_AR);
            } else {
                AppLocale.putLang(this, Consts.LANG_ENG);
            }
            reloadActivity();
            return true;
        } else if (item.getItemId() == R.id.item_search) {
            SearchActivity.toSearchActivity(MainActivity.this);
            return true;
        }

        return false;
    }

    private void reloadActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

}
