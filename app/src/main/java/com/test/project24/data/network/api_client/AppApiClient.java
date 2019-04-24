package com.test.project24.data.network.api_client;


import com.test.project24.data.network.models.ChangesModel;
import com.test.project24.data.network.models.MovieSearchModel;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.utils.Consts;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author goharali
 */

public interface AppApiClient {

    @GET("search/movie?api_key=" + Consts.API_KEY + "&include_adult=" + false)
    Observable<MovieSearchModel> searchMovieByQuery(@Query("language") String lang,
                                                    @Query("query") String query,
                                                    @Query("page") int page);


    @GET("movie/changes?api_key=" + Consts.API_KEY + "&include_adult=" + false)
    Observable<ChangesModel> getChangesList(@Query("language") String lang,
                                            @Query("start_date") String startDate,
                                            @Query("end_date") String endDate,
                                            @Query("page") int page);

    @GET("movie/{movie_id}?api_key=" + Consts.API_KEY + "&append_to_response=videos")
    Observable<MovieDetail> getMovieDetail(@Path("movie_id") String movieId,
                                           @Query("language") String lang);

}
