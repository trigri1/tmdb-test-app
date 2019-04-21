package com.test.project24.di.modules;

import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.test.project24.BuildConfig;
import com.test.project24.MyApp;
import com.test.project24.data.AppDataManager;
import com.test.project24.data.IDataManager;
import com.test.project24.data.database.AppRoomDataBase;
import com.test.project24.data.database.DatabaseHelper;
import com.test.project24.data.database.IDatabaseHelper;
import com.test.project24.data.network.AppApiHelper;
import com.test.project24.data.network.IAppApiHelper;
import com.test.project24.data.network.api_client.AppApiClient;
import com.test.project24.data.sharedprefs.AppPrefsHelper;
import com.test.project24.data.sharedprefs.IAppPrefsHelper;
import com.test.project24.di.annotations.ApplicationContext;
import com.test.project24.di.annotations.CacheInfo;
import com.test.project24.di.annotations.PreferenceInfo;
import com.test.project24.utils.AppLogger;
import com.test.project24.utils.Consts;
import com.test.project24.utils.NetworkUtils;
import com.test.project24.utils.rx.AppSchedulerProvider;
import com.test.project24.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gohar Ali on 30/10/2017.
 */

@Module
public class ApplicationModule {


//    private MyApp myApp;

//    public ApplicationModule(MyApp myApp) {
//        this.myApp = myApp;
//    }

//    @Provides
//    public MyApp provideApplication(MyApp myApp) {
//        return myApp;
//    }


    @Provides
    SchedulerProvider schedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @ApplicationContext
    public Context provideContext(MyApp myApp) {
        return myApp;
    }


    @Provides
    @PreferenceInfo
    public String provideSharedPrefsName() {
        return Consts.PREFS_NAME;
    }

    @Provides
    @Singleton
    IDataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }


    @Provides
    @Singleton
    IAppApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }


    @Provides
    @Singleton
    IAppPrefsHelper providePrefsHelper(AppPrefsHelper appPrefsHelper) {
        return appPrefsHelper;
    }

    @Provides
    @Singleton
    IDatabaseHelper provideDatabaseHelper(DatabaseHelper databaseHelper) {
        return databaseHelper;
    }

    @Provides
    @Singleton
    AppRoomDataBase provideRoomDataBase(@ApplicationContext Context context) {
        return Room
                .databaseBuilder(context, AppRoomDataBase.class, Consts.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }


    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    NotificationManager provideNotificationManager(@ApplicationContext Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Provides
    @Named("non_cached")
    @Singleton
    AppApiClient provideAppApiClient(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(AppApiClient.class);

    }


    @Provides
    @Named("cached")
    @Singleton
    AppApiClient provideCachedAppApiClient(@CacheInfo OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(AppApiClient.class);

    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {

        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)// Set connection timeout
                .readTimeout(60, TimeUnit.SECONDS)// Read timeout
                .writeTimeout(60, TimeUnit.SECONDS)// Write timeout
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }


    @CacheInfo
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClientCached(@CacheInfo Cache cache
            , @ApplicationContext Context context
            , HttpLoggingInterceptor httpLoggingInterceptor) {

        return new OkHttpClient.Builder()
                .cache(cache)// Add cache
                .connectTimeout(60, TimeUnit.SECONDS)// Set connection timeout
                .readTimeout(60, TimeUnit.SECONDS)// Read timeout
                .writeTimeout(60, TimeUnit.SECONDS)// Write timeout
                .addNetworkInterceptor(getRewriteInterceptor(context))// Add a custom cache interceptor， Note that it needs to be used here. .addNetworkInterceptor
                .addInterceptor(getRewriteInterceptor(context))
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> AppLogger.e("Retrofit", message))
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @Provides
    @Singleton
    @CacheInfo
    File provideCacheDirectory(@CacheInfo String directoryName, @ApplicationContext Context context) {
        try {
//            return new File(Environment.getExternalStorageDirectory(), directoryName);
            return new File(context.getCacheDir(), directoryName);
        } catch (Exception e) {
            AppLogger.e("provideCacheDirectory", "Exception", e);
        }

        return null;
    }

    @Provides
    @Singleton
    @CacheInfo
    String provideResponseCacheDirectoryName() {
        return Consts.HTTP_CACHE_DIRECTORY;
    }


    @Provides
    @Singleton
    @CacheInfo
    Cache getCache(@CacheInfo File httpCacheDirectory) {
//        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), Consts.HTTP_CACHE_DIRECTORY);
        // Here to facilitate the file directly on the SD Kagan catalog HttpCache in
        // ， Generally put in context.getCacheDir() in
        int cacheSize = 10 * 1024 * 1024;// Set cache file size 10M
        return new Cache(httpCacheDirectory, cacheSize);
    }


    private Interceptor getRewriteInterceptor(Context context) {

        return chain -> {
            Request originalRequest = chain.request();
            String cacheHeaderValue = NetworkUtils.isNetworkConnected(context)
                    ? "public, max-age=" + Consts.HTTP_CACHE_MAX_AGE
                    : "public, only-if-cached, max-stale=" + Consts.HTTP_CACHE_STALE_MAX_AGE;
            Request request = originalRequest.newBuilder().build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build();
        };
    }


}
