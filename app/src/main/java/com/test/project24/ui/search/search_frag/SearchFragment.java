package com.test.project24.ui.search.search_frag;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.test.project24.BR;
import com.test.project24.R;
import com.test.project24.data.network.models.MovieModel;
import com.test.project24.databinding.FragmentSearchBinding;
import com.test.project24.ui.base.BaseFragment;
import com.test.project24.ui.search.search_frag.adapter.SearchAdapter;
import com.test.project24.utils.Consts;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author goharali
 */

public class SearchFragment extends BaseFragment<FragmentSearchBinding, SearchViewModel>
        implements HasSupportFragmentInjector, SearchNavigator {

    private static String SELECTED_LANG = "en";
    private static final String PARAM = "param";
    private static final String PARAM1 = "param1";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    SearchAdapter adapter;

    @Inject
    ViewModelProvider.Factory factory;

    private List<MovieModel> allResults = new ArrayList<>();

    private String query;

    private Boolean callNext;
    private int page = 1;

    public static SearchFragment newInstance(String query, String selectedLand) {

        Bundle args = new Bundle();
        args.putString(PARAM, query);
        args.putString(PARAM1, selectedLand);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(PARAM);
            SELECTED_LANG = getArguments().getString(PARAM1);
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViewModel().setShowLoader(true);
        getViewModel().getSearchResponse(SELECTED_LANG, query, page);
        observeSearchResult();
        setAdapter();

        getViewModel().setViewNavigator(this);
        getViewModel().setQuery(query);
    }


    public void fetchSearchResults(String query) {
        page = 1;
        callNext = false;
        this.query = query;
        allResults.clear();
        getViewModel().moviesList.clear();
        getViewModel().setQuery(query);
        getViewModel().getSearchResponse(SELECTED_LANG, query, page);
    }


    private void setAdapter() {

        getViewDataBinding().rvSearch.setAdapter(adapter);

        getViewDataBinding().rvSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) getViewDataBinding().rvSearch.getLayoutManager();

                int lastPosition = manager.findLastVisibleItemPosition();

                if (lastPosition == getViewDataBinding().rvSearch.getAdapter().getItemCount() - 1 && callNext) {
                    callNext = false;
                    getViewModel().getSearchResponse(SELECTED_LANG, query, page);
                }
            }
        });
    }


    private void observeSearchResult() {
        getViewModel().getSearchResults().observe(this, movieSearchModel -> {

            if (movieSearchModel != null
                    && movieSearchModel.getResults() != null
                    && movieSearchModel.getResults().size() > 0) {

                if (movieSearchModel.getResults().size() < Consts.LIMIT) {
                    callNext = false;
                } else {
                    page++;
                    callNext = true;
                }

                adapter.setShowLoader(callNext);

                allResults.addAll(movieSearchModel.getResults());
                getViewModel().setShowResults(true);

            } else {
                if (page == 1) {
                    getViewModel().setShowResults(false);
                }
            }

        });
    }

    @Override
    public SearchViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SearchViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onErrorReceived(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRetryClicked() {
        hideKeyboard();
        getViewModel().setShowLoader(true);
        getViewModel().getSearchResponse(SELECTED_LANG, query, page);
    }
}
