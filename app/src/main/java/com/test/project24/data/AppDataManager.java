package com.test.project24.data;


import com.test.project24.data.database.DatabaseHelper;
import com.test.project24.data.database.IDatabaseHelper;
import com.test.project24.data.database.daos.UserDao;
import com.test.project24.data.network.AppApiHelper;
import com.test.project24.data.network.IAppApiHelper;
import com.test.project24.data.network.models.ChangesModel;
import com.test.project24.data.network.models.MovieSearchModel;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.data.sharedprefs.AppPrefsHelper;
import com.test.project24.data.sharedprefs.IAppPrefsHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * @author Gohar Ali
 * This class is responsible for implementing all the methods that are declared in
 * @see IDataManager and return proper data types for each mathod. This is main class that is used
 * to fetch user or application data.
 * This class is singlton (Based on singlton Design Pattern).
 */

@Singleton
public class AppDataManager implements IDataManager {


    private IDatabaseHelper databaseHelper;
    private IAppPrefsHelper appPrefsHelper;
    private IAppApiHelper appApiHelper;

    /**
     * Constructor is provided parameters using
     * Dependency Injection (Dagger2)
     *
     * @see com.test.project24.di.modules.ApplicationModule#provideApiHelper(AppApiHelper)
     * @see com.test.project24.di.modules.ApplicationModule#provideDatabaseHelper(DatabaseHelper)
     * @see com.test.project24.di.modules.ApplicationModule#providePrefsHelper(AppPrefsHelper) (AppApiHelper)
     */
    @Inject
    public AppDataManager(IDatabaseHelper databaseHelper
            , IAppApiHelper appApiHelper
            , IAppPrefsHelper appPrefsHelper) {

        this.databaseHelper = databaseHelper;
        this.appApiHelper = appApiHelper;
        this.appPrefsHelper = appPrefsHelper;
    }

    public IAppApiHelper getAppApiHelper() {
        return appApiHelper;
    }

    public IAppPrefsHelper getAppPrefsHelper() {
        return appPrefsHelper;
    }


    @Override
    public String getLang() {
        return appPrefsHelper.getLang();
    }

    @Override
    public void putLang(String lang) {
        appPrefsHelper.putLang(lang);
    }

    @Override
    public UserDao getUserDao() {
        return null;
    }

    @Override
    public Observable<MovieSearchModel> searchMovieByQuery(String lang, String query, int page) {
        return appApiHelper.searchMovieByQuery(lang, query, page);
    }

    @Override
    public Observable<ChangesModel> getChangesList(String lang, String startDate, String endDate, int page) {
        return appApiHelper.getChangesList(lang, startDate, endDate, page);
    }

    @Override
    public Observable<MovieDetail> getMovieDetail(String movieId, String lang) {
        return appApiHelper.getMovieDetail(movieId, lang);
    }

}
