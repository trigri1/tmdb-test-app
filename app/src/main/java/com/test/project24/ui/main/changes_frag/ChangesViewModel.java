package com.test.project24.ui.main.changes_frag;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import com.test.project24.data.IDataManager;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.AppLogger;
import com.test.project24.utils.rx.SchedulerProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class ChangesViewModel extends BaseViewModel<ChangesNavigator> {

    private static final String TAG = ChangesViewModel.class.getSimpleName();


    public ObservableList<MovieDetail> changesList = new ObservableArrayList<>();
    private Map<Integer, Integer> changesPositionMap = new HashMap<>();

    private ObservableBoolean showResults = new ObservableBoolean(true);
    private ObservableInt gridSpan = new ObservableInt(3);
    private ObservableBoolean showLoader = new ObservableBoolean(true);

    public ObservableField<String> startDate = new ObservableField<>();
    public ObservableField<String> endDate = new ObservableField<>();

    private int index = 0;


    public ChangesViewModel(IDataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {

        super(dataManager, schedulerProvider, compositeDisposable);
        startDate.set("Start Date");
        endDate.set("End Date");
        setShowLoader(false);
    }

    public Disposable getChangesList(String lang, String startDate, String endDate, int page) {
        setShowLoader(true);

        return getDataManager().getChangesList(lang, startDate, endDate, page)
                .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui())
                .subscribe(
                        changesModel -> {
                            setShowLoader(false);
                            setShowResults(true);
                            changesList.addAll(adultFilter(changesModel.getResults()));
                            makeMapWithItemPositions(changesList);
                            getViewNavigator().onChangesReceived(changesList);
                        },
                        throwable -> {
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

                            AppLogger.e(TAG, "getChangesList() Failed", throwable);
                        });

    }

    public List<MovieDetail> adultFilter(List<MovieDetail> list) {
        List<MovieDetail> updatedList = new ArrayList<>();
        for (MovieDetail movie : list) {
            if (!movie.getAdult()) {
                updatedList.add(movie);
            }
        }
        return updatedList;
    }


    private void makeMapWithItemPositions(List<MovieDetail> list) {
        for (int i = 0; i < list.size(); i++) {
            changesPositionMap.put(list.get(i).getId(), i);
        }
    }


    public Disposable getMovieDetail(int movieId) {
        return getDataManager().getMovieDetail(movieId + "", "en")
                .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui())
                .subscribe(
                        movieDetail -> {
                            int id = movieDetail.getId();
                            if (changesPositionMap.containsKey(id)) {
                                index = changesPositionMap.get(id);
                                if (changesList.size() > index) {
                                    changesList.set(index, movieDetail);
                                }
                                callNextMovieDetail();
                            }
                        },
                        throwable -> {
                            callNextMovieDetail();
                            AppLogger.e(TAG, "getMovieDetail() Failed", throwable);
                        });
    }


    private void callNextMovieDetail() {
        if (++index < changesList.size()) {
            addToCompositeDisposable(getMovieDetail(changesList.get(index).getId()));
        }
    }


    public ObservableBoolean getShowLoader() {
        return showLoader;
    }

    public void setShowLoader(boolean showLoader) {
        this.showLoader.set(showLoader);
    }

    public void setChangesList(ObservableList<MovieDetail> changesList) {
        this.changesList = changesList;
    }

    public ObservableInt getGridSpan() {
        return gridSpan;
    }

    public void setGridSpan(int gridSpan) {
        this.gridSpan.set(gridSpan);
    }

    public ObservableBoolean getShowResults() {
        return showResults;
    }

    public void setShowResults(boolean showResults) {
        this.showResults.set(showResults);
    }


    //listeners
    public void onRetryClicked() {
        showResults.set(true);
        getViewNavigator().onRetryClicked();
    }

    public void onStartDateClicked() {
        getViewNavigator().onStartDateClicked();
    }

    public void onEndDateClicked() {
        getViewNavigator().onEndDateClicked();
    }

    public void onFetchClicked() {
        changesList.clear();
        getViewNavigator().onFetchClicked();
    }

}
