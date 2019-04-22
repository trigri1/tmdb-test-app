package com.test.project24.ui.search;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.inputmethod.EditorInfo;

import com.test.project24.BR;
import com.test.project24.R;
import com.test.project24.databinding.ActivitySearchBinding;
import com.test.project24.ui.base.BaseActivity;
import com.test.project24.ui.search.search_frag.SearchFragment;
import com.test.project24.utils.CommonUtils;
import com.test.project24.utils.locale.AppLocale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchMainViewModel>
        implements HasSupportFragmentInjector, SearchMainNavigator {

    private static String SELECTED_LANG = "en";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory appViewModelFactory;

    private SearchFragment searchFragment;

    public static void toSearchActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();

    }


    private void addFragment(String query) {

        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance(query, SELECTED_LANG);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, searchFragment)
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        } else {
            searchFragment.fetchSearchResults(query);
        }


    }

    @Override
    public void initActivity() {
        SELECTED_LANG = AppLocale.getLang(this);

        getViewDataBinding().inHeader.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = v.getText().toString().trim();
                if (query.isEmpty()) {
                    CommonUtils.showToast(SearchActivity.this, R.string.eneter_search_keyword);
                    return true;
                }
                addFragment(query);
                return false;
            }
            return false;
        });
    }

    @Override
    public SearchMainViewModel getViewModel() {
        return ViewModelProviders.of(this, appViewModelFactory).get(SearchMainViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
