package com.test.project24.ui.main.changes_frag;

import com.test.project24.data.IDataManager;
import com.test.project24.data.network.models.ChangesModel;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.utils.rx.SchedulerProvider;
import com.test.project24.utils.rx.TrampolineSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ChangesViewModelTest {

    private int dataSize = 10;
    private int movieId = 1234;

    @Mock
    IDataManager dataManager;

    private SchedulerProvider schedulerProvider;

    private CompositeDisposable compositeDisposable;

    private ChangesViewModel changesViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        compositeDisposable = new CompositeDisposable();
        schedulerProvider = new TrampolineSchedulerProvider();
        changesViewModel = new ChangesViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Test
    public void testAdultContentRemoved() {
        List<MovieDetail> list = changesViewModel.adultFilter(getMovieData());
        assertEquals(list.size(), dataSize / 2);
    }

    @Test
    public void testGetMovieDetail() {
        when(dataManager.getMovieDetail(movieId + "", "en")).thenReturn(getMovieObservable());
        changesViewModel.getMovieDetail(movieId);
    }

    @Test
    public void testGetChangesList() {
        when(dataManager.getChangesList("en", "", "", 1)).thenReturn(getChangesObservable());
        changesViewModel.getChangesList("en", "", "", 1);

    }

    private List<MovieDetail> getMovieData() {
        List<MovieDetail> list = new ArrayList<>();

        for (int i = 0; i < dataSize; i++) {
            MovieDetail movieDetail = new MovieDetail();
            movieDetail.setId(i);
            if (i % 2 == 0) {
                movieDetail.setAdult(true);
            } else {
                movieDetail.setAdult(false);
            }

            list.add(movieDetail);
        }
        return list;
    }

    private Observable<MovieDetail> getMovieObservable() {
        MovieDetail movieDetail = new MovieDetail();
        movieDetail.setId(movieId);

        return Observable.just(movieDetail);
    }

    private Observable<ChangesModel> getChangesObservable() {
        ChangesModel movieDetail = new ChangesModel();
        return Observable.just(movieDetail);
    }
}