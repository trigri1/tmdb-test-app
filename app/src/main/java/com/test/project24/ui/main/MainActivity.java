package com.test.project24.ui.main;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.test.project24.utils.CommonUtils;
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

    private MainViewModel mainViewModel;

    ActivityMainBinding binding;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        addFragment("");
    }

    @Override
    public void initActivity() {

        binding = getViewDataBinding();
        SELECTED_LANG = AppLocale.getLang(this);
    }

    private void addFragment(String query) {

//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.container, SearchFragment.newInstance(query, SELECTED_LANG))
//                .commit();
//        getSupportFragmentManager().executePendingTransactions();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, ChangesFragment.newInstance())
                .commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    @Override
    public MainViewModel getViewModel() {
        mainViewModel = ViewModelProviders.of(this, appViewModelFactory).get(MainViewModel.class);
        return mainViewModel;
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
        addSearchActions(menu);


        return true;
    }


    private void addSearchActions(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.item_search)
                .getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setMaxWidth(Integer.MAX_VALUE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideKeyboard();

                if (query.isEmpty()) {

                    CommonUtils.showToast(MainActivity.this
                            , R.string.eneter_search_keyword);
                    return true;
                }
                addFragment(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
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

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
