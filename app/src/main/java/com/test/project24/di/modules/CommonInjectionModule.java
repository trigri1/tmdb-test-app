package com.test.project24.di.modules;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Gohar Ali
 */


@Module
public class CommonInjectionModule {
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
