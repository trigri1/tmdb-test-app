package com.test.project24.ui.main;

import android.arch.lifecycle.MutableLiveData;

import com.test.project24.data.IDataManager;
import com.test.project24.data.network.models.UserObj;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.AppLogger;
import com.test.project24.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Gohar Ali on 16/01/2018.
 */

public class MainViewModel extends BaseViewModel<IMainNavigator> {


    private List<String> queriesList = new ArrayList<>();
    private MutableLiveData<List<String>> searchQueries;


    public MainViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        searchQueries = new MutableLiveData<>();
    }

}
