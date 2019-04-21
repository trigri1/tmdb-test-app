package com.test.project24.data.network;


import com.test.project24.data.network.models.ChangesModel;
import com.test.project24.data.network.models.MovieSearchModel;
import com.test.project24.data.network.models.detail.MovieDetail;

import io.reactivex.Observable;

/**
 * Created by Gohar Ali on 30/10/2017.
 *
 * @author Gohar Ali
 * * This interface holds all methods that are used to send and fetch data
 * from Server. This interface is implemented by
 * @see AppApiHelper
 */

public interface IAppApiHelper {

    Observable<MovieSearchModel> searchMovieByQuery(String lang, String query, int page);

    Observable<ChangesModel> getChangesList(String lang, String startDate, String endDate, int page);

    Observable<MovieDetail> getMovieDetail(String movieId, String lang);

}
