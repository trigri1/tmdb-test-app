package com.test.project24.ui.main.changes_frag;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

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

import io.reactivex.functions.Consumer;
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


    public ChangesViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        startDate.set("Start Date");
        endDate.set("End Date");
        setShowLoader(false);
    }

    public void getChangesList(String lang, String startDate, String endDate, int page) {
        setShowLoader(true);
        getCompositeDisposable().add(
                getDataManager().getChangesList(lang, startDate, endDate, page)
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
                                }));

    }

    private List<MovieDetail> adultFilter(List<MovieDetail> list) {
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

    private int index = 0;

    public void getMovieDetail(int movieId) {
        getCompositeDisposable().add(getDataManager().getMovieDetail(movieId + "", "en")
                .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MovieDetail>() {
                    @Override
                    public void accept(MovieDetail movieDetail) throws Exception {

                        int movieId = movieDetail.getId();

                        if (changesPositionMap.containsKey(movieId)) {
                            index = changesPositionMap.get(movieId);
                            if (changesList.size() > index) {
                                changesList.set(index, movieDetail);
                            }
                            AppLogger.e(TAG, index + " passed of " + changesList.size());
                            callNextMovieDetail();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callNextMovieDetail();
                        AppLogger.e(TAG, "getMovieDetaile() Failed", throwable);
                    }
                }));
    }

    private void callNextMovieDetail() {
        if (++index < changesList.size()) {
            getMovieDetail(changesList.get(index).getId());
        } else {
            AppLogger.e(TAG, index + " of " + changesList.size() + " ====> Ended");
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

    public void onRetryClicked(View view) {
        showResults.set(true);
        getViewNavigator().onRetryClicked();
    }


    public void onStartDateClicked(View view) {
        getViewNavigator().onStartDateClicked();
    }

    public void onEndDateClicked(View view) {
        getViewNavigator().onEndDateClicked();
    }

    public void onFetchClicked(View view) {
        changesList.clear();
        getViewNavigator().onFetchClicked();
    }

}
