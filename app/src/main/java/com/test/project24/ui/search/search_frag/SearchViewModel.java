package com.test.project24.ui.search.search_frag;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;

import com.test.project24.data.IDataManager;
import com.test.project24.data.network.models.MovieModel;
import com.test.project24.data.network.models.MovieSearchModel;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.AppLogger;
import com.test.project24.utils.rx.SchedulerProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by Gohar Ali on 24/02/2018.
 */

public class SearchViewModel extends BaseViewModel<SearchNavigator> {

    private static final String TAG = SearchViewModel.class.getSimpleName();

    private ObservableBoolean showLoader = new ObservableBoolean(true);

    private MutableLiveData<MovieSearchModel> searchResult;

    private ObservableField<String> query = new ObservableField<>();

    public ObservableList<MovieModel> moviesList = new ObservableArrayList<>();

    private ObservableBoolean showResults = new ObservableBoolean(true);

    public SearchViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        searchResult = new MutableLiveData<>();
    }


    public void getSearchResponse(String lang, String query, int page) {
        setShowLoader(true);
        getCompositeDisposable().add(getDataManager().searchMovieByQuery(lang, query, page)
                .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui())
                .subscribe(
                        movieSearchModel -> {
                            setShowLoader(false);
                            searchResult.setValue(movieSearchModel);
                            moviesList.addAll(movieSearchModel.getResults());
                        },
                        throwable -> {
                            searchResult.setValue(null);
                            setShowLoader(false);
                            setShowResults(false);
                            if (throwable instanceof HttpException) {
                                String errorBody = ((HttpException) throwable).response().errorBody().string();
                                JSONObject jsonError = new JSONObject(errorBody);
                                if (jsonError.has("status_message")) {
                                    getViewNavigator().onErrorReceived(jsonError.getString("status_message"));
                                }
                                AppLogger.e(TAG, "errorBody ===>" + errorBody);
                            } else if (throwable instanceof SocketTimeoutException) {
                                getViewNavigator().onErrorReceived("Request timeout error.");
                            } else if (throwable instanceof IOException) {
                                getViewNavigator().onErrorReceived("Check your network connection.");
                            } else {
                                getViewNavigator().onErrorReceived("Unknown error occurred.");
                            }

                            AppLogger.e(TAG, "getSearchResponse() Failed", throwable);
                        }));

    }


    public LiveData<MovieSearchModel> getSearchResults() {
        return searchResult;
    }


    public ObservableField<String> getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query.set(query);
    }

    public ObservableBoolean getShowResults() {
        return showResults;
    }

    public void setShowResults(boolean show) {
        showResults.set(show);
    }

    public void onRetryClicked(View view) {
        showResults.set(true);
        getViewNavigator().onRetryClicked();
    }

    public ObservableBoolean getShowLoader() {
        return showLoader;
    }

    public void setShowLoader(boolean showLoader) {
        this.showLoader.set(showLoader);
    }
}
