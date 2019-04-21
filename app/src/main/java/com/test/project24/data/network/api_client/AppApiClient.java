package com.test.project24.data.network.api_client;


import com.test.project24.BuildConfig;
import com.test.project24.data.network.models.ChangesModel;
import com.test.project24.data.network.models.MovieSearchModel;
import com.test.project24.data.network.models.detail.MovieDetail;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Gohar Ali on 10/11/2017.
 */

public interface AppApiClient {

    @GET("search/movie?api_key=" + BuildConfig.API_KEY + "&include_adult=" + false)
    Observable<MovieSearchModel> searchMovieByQuery(@Query("language") String lang,
                                                    @Query("query") String query,
                                                    @Query("page") int page);


    @GET("movie/changes?api_key=" + BuildConfig.API_KEY + "&include_adult=" + false)
    Observable<ChangesModel> getChangesList(@Query("language") String lang,
                                            @Query("start_date") String startDate,
                                            @Query("end_date") String endDate,
                                            @Query("page") int page);

    @GET("movie/{movie_id}?api_key=" + BuildConfig.API_KEY)
    Observable<MovieDetail> getMovieDetail(@Path("movie_id") String movieId,
                                           @Query("language") String lang);

}
