package com.test.project24.ui.detail;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.test.project24.data.IDataManager;
import com.test.project24.data.network.models.detail.Genre;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.data.network.models.detail.SpokenLanguage;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.AppLogger;
import com.test.project24.utils.Consts;
import com.test.project24.utils.rx.SchedulerProvider;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.HttpException;

public class DetailViewModel extends BaseViewModel<DetailNavigator> {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    private MovieDetail movieModel;

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> detail = new ObservableField<>();
    public ObservableField<String> language = new ObservableField<>();
    public ObservableField<String> genres = new ObservableField<>();
    public ObservableField<String> releaseDate = new ObservableField<>();
    public ObservableField<String> image = new ObservableField<>();
    private ObservableField<String> imageSize = new ObservableField<>();

    private ObservableBoolean showResults = new ObservableBoolean(false);
    private ObservableBoolean showLoader = new ObservableBoolean(true);


    @Inject
    public DetailViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void getMovieDetail(int movieId) {
        setShowLoader(true);
        getCompositeDisposable().add(getDataManager().getMovieDetail(movieId + "", "en")
                .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui())
                .subscribe(
                        movieDetail -> {
                            setShowResults(true);
                            setShowLoader(false);
                            setMovieModel(movieDetail);
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

                            AppLogger.e(TAG, "getMovieDetail() Failed", throwable);
                        }));

    }

    public void setMovieModel(MovieDetail movieModel) {
        this.movieModel = movieModel;

        setTitle(movieModel.getTitle());
        setDetail(movieModel.getOverview());
        setLanguage(getLanguagesString(movieModel.getSpokenLanguages(), ","));
        setReleaseDate(movieModel.getReleaseDate());
        setGenres(getGenresString(movieModel.getGenres(), ","));

        if (movieModel.getBackdropPath() != null && !movieModel.getBackdropPath().isEmpty())
            setImage(movieModel.getBackdropPath());
        else
            setImage(movieModel.getPosterPath());

        setImageSize(Consts.SIZE_BACKDROP);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDetail(String detail) {
        this.detail.set(detail);
    }

    public void setLanguage(String lang) {
        language.set(lang);
    }

    public void setReleaseDate(String date) {
        releaseDate.set(date);
    }

    public String getGenresString(List<Genre> list, String separator) {
        String genresStr = "";
        if (list.isEmpty()) {
            return "";
        }

        String first = list.get(0).getName();

        if (first != null) {
            genresStr = first;
        }

        for (int i = 1; i < list.size(); i++) {
            genresStr = genresStr.concat(separator)
                    .concat(" ")
                    .concat(list.get(i).getName());
        }

        return genresStr;
    }

    public String getLanguagesString(List<SpokenLanguage> list, String separator) {
        String genresStr = "";
        if (list.isEmpty()) {
            return "";
        }

        String first = list.get(0).getName();

        if (first != null) {
            genresStr = first;
        }

        for (int i = 1; i < list.size(); i++) {
            genresStr = genresStr.concat(separator)
                    .concat(" ")
                    .concat(list.get(i).getName());
        }

        return genresStr;
    }

    public void setGenres(String g) {
        genres.set(g);
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    public MovieDetail getMovieModel() {
        return movieModel;
    }

    public void setImageSize(String image) {
        this.imageSize.set(image);
    }

    public ObservableField<String> getImageSize() {
        return imageSize;
    }

    public ObservableBoolean getShowLoader() {
        return showLoader;
    }

    public void setShowLoader(boolean showLoader) {
        this.showLoader.set(showLoader);
    }

    public void setShowResults(boolean showResults) {
        this.showResults.set(showResults);
    }

    public ObservableBoolean getShowResults() {
        return showResults;
    }


    public void onPlayClicked(View view) {
        getViewNavigator().onPlayClicked(view, movieModel);
    }

}
