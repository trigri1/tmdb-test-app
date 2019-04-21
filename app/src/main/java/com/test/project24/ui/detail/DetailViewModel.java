package com.test.project24.ui.detail;

import android.databinding.ObservableField;
import android.view.View;

import com.test.project24.data.IDataManager;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.AppLogger;
import com.test.project24.utils.Consts;
import com.test.project24.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class DetailViewModel extends BaseViewModel<DetailNavigator> {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    private MovieDetail movieModel;

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> detail = new ObservableField<>();
    public ObservableField<String> image = new ObservableField<>();
    private ObservableField<String> imageSize = new ObservableField<>();


    @Inject
    public DetailViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDetail(String detail) {
        this.detail.set(detail);
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    public MovieDetail getMovieModel() {
        return movieModel;
    }

    public void getMovieDetail(int movieId) {

        getCompositeDisposable().add(getDataManager().getMovieDetail(movieId + "", "en")
                .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MovieDetail>() {
                    @Override
                    public void accept(MovieDetail movieDetail) throws Exception {
                        setMovieModel(movieDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        AppLogger.e(TAG, "getMovieDetail() Failed", throwable);
                    }
                }));

    }

    public void setMovieModel(MovieDetail movieModel) {
        this.movieModel = movieModel;

        setTitle(movieModel.getTitle());
        setDetail(movieModel.getOverview());


        if (movieModel.getBackdropPath() != null && !movieModel.getBackdropPath().isEmpty())
            setImage(movieModel.getBackdropPath());
        else
            setImage(movieModel.getPosterPath());

        setImageSize(Consts.SIZE_BACKDROP);
    }

    public void setImageSize(String image) {
        this.imageSize.set(image);
    }

    public ObservableField<String> getImageSize() {
        return imageSize;
    }


    public void onPlayClicked(View view) {
        getViewNavigator().onPlayClicked(view, movieModel);
    }

}
