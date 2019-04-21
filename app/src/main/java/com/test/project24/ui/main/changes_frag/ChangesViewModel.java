package com.test.project24.ui.main.changes_frag;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;

import com.test.project24.data.IDataManager;
import com.test.project24.data.network.models.ChangesModel;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.AppLogger;
import com.test.project24.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class ChangesViewModel extends BaseViewModel<ChangesNavigator> {

    private static final String TAG = ChangesViewModel.class.getSimpleName();

    private ObservableBoolean showLoader = new ObservableBoolean(true);
    public ObservableList<MovieDetail> changesList = new ObservableArrayList<>();
    private ObservableBoolean showResults = new ObservableBoolean(true);

    private Map<Integer, Integer> changesPositionMap = new HashMap<>();

    private ObservableInt gridSpan = new ObservableInt(3);

    public ChangesViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getChangesList(String lang, String startDate, String endDate, int page) {
        setShowLoader(true);
        getCompositeDisposable().add(
                getDataManager().getChangesList(lang, startDate, endDate, page)
                        .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<ChangesModel>() {
                            @Override
                            public void accept(ChangesModel changesModel) throws Exception {
                                setShowLoader(false);
                                changesList.addAll(adultFilter(changesModel.getResults()));
                                makeMapWithItemPositions(changesList);
                                getViewNavigator().onChangesReceived(changesList);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                setShowLoader(false);
                                AppLogger.e(TAG, "getChangesList() Failed", throwable);
                            }
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
                        AppLogger.e(TAG, index + " failed of " + changesList.size());
                        callNextMovieDetail();
                        AppLogger.e(TAG, "getMovieDetaile() Failed", throwable);
                    }
                }));
    }

    private void callNextMovieDetail() {
//        AppLogger.e(TAG, index + " of " + changesList.size());

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

    public void setShowResults(ObservableBoolean showResults) {
        this.showResults = showResults;
    }

    public void onRetryClicked(View view) {
        showResults.set(true);
        getViewNavigator().onRetryClicked();
    }
}
