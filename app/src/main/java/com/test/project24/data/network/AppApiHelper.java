package com.test.project24.data.network;

import android.content.Context;

import com.test.project24.data.network.api_client.AppApiClient;
import com.test.project24.data.network.models.ChangesModel;
import com.test.project24.data.network.models.MovieSearchModel;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.di.annotations.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * @author Gohar Ali
 * This class is responsible for implementing all the methods that are declared in
 * @see IAppApiHelper and return proper data types for each mathod.
 * This class is singlton (Based on singlton Design Pattern).
 */

@Singleton
public class AppApiHelper implements IAppApiHelper {

    private Context context;

    private AppApiClient appApiClientCached;
    private AppApiClient appApiClient;

    /**
     * Constructor is provided parameters using
     * Dependency Injection (Dagger2)
     * <p>
     * {@link com.test.project24.di.modules.ApplicationModule}
     */

    @Inject
    public AppApiHelper(@ApplicationContext Context context,
                        @Named("non_cached") AppApiClient appApiClient,
                        @Named("cached") AppApiClient appApiClientCached) {
        this.context = context;
        this.appApiClient = appApiClient;
        this.appApiClientCached = appApiClientCached;
    }

    @Override
    public Observable<MovieSearchModel> searchMovieByQuery(String lang, String query, int page) {
        return appApiClientCached.searchMovieByQuery(lang, query, page);
    }

    @Override
    public Observable<ChangesModel> getChangesList(String lang, String startDate, String endDate, int page) {
        return appApiClientCached.getChangesList(lang, startDate, endDate, page);
    }

    @Override
    public Observable<MovieDetail> getMovieDetail(String movieId, String lang) {
        return appApiClientCached.getMovieDetail(movieId, lang);
    }

}
