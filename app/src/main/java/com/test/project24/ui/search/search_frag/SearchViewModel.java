package com.test.project24.ui.search.search_frag;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.test.project24.data.IDataManager;
import com.test.project24.data.network.models.MovieModel;
import com.test.project24.data.network.models.MovieSearchModel;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.AppLogger;
import com.test.project24.utils.rx.SchedulerProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

/**
 * @author goharali
 */

public class SearchViewModel extends BaseViewModel<SearchNavigator> {

    private static final String TAG = SearchViewModel.class.getSimpleName();

    private ObservableBoolean showLoader = new ObservableBoolean(true);

    private MutableLiveData<MovieSearchModel> searchResult;

    private ObservableField<String> query = new ObservableField<>();

    public ObservableList<MovieModel> moviesList = new ObservableArrayList<>();

    private ObservableBoolean showResults = new ObservableBoolean(true);

    public SearchViewModel(IDataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {

        super(dataManager, schedulerProvider, compositeDisposable);
        searchResult = new MutableLiveData<>();
    }


    public void getSearchResponse(String lang, String query, int page) {
        if (page == 1) {
            setShowLoader(true);
        }
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

    public ObservableBoolean getShowLoader() {
        return showLoader;
    }

    public void setShowLoader(boolean showLoader) {
        this.showLoader.set(showLoader);
    }

    public void onRetryClicked() {
        showResults.set(true);
        getViewNavigator().onRetryClicked();
    }
}
