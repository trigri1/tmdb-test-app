package com.test.project24.ui.player;

import android.databinding.ObservableBoolean;

import com.test.project24.data.IDataManager;
import com.test.project24.ui.base.BaseViewModel;
import com.test.project24.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author goharali
 */

public class PlayerViewModel extends BaseViewModel<PlayerViewModel> {

    private ObservableBoolean showPlayerLoader = new ObservableBoolean(true);
    private ObservableBoolean showMainPlayButton = new ObservableBoolean(false);
    private ObservableBoolean showPlayPauseSection = new ObservableBoolean(false);

    @Inject
    public PlayerViewModel(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    public ObservableBoolean getShowPlayerLoader() {
        return showPlayerLoader;
    }

    public void setShowPlayerLoader(boolean showPlayerLoader) {
        this.showPlayerLoader.set(showPlayerLoader);
    }

    public ObservableBoolean getShowMainPlayButton() {
        return showMainPlayButton;
    }

    public void setShowMainPlayButton(boolean showMainPlayButton) {
        this.showMainPlayButton.set(showMainPlayButton);

    }

    public ObservableBoolean getShowPlayPauseSection() {
        return showPlayPauseSection;
    }

    public void setShowPlayPauseSection(boolean showPlayPauseSection) {
        this.showPlayPauseSection.set(showPlayPauseSection);
    }
}
