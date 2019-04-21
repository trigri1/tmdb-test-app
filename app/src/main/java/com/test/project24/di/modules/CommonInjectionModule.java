package com.test.project24.di.modules;

import com.test.project24.di.annotations.PerActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Gohar Ali
 *         Classes annotated with @module are used to provide dependencies (Dagger2)
 *         This class is used to provide dependencie for actitivtes. It provide requied dependency using methods
 *         annotated with @Provides
 */


@Module
public class CommonInjectionModule {

    @PerActivity
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
