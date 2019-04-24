package com.test.project24.di.components;

import android.content.Context;

import com.test.project24.MyApp;
import com.test.project24.data.IDataManager;
import com.test.project24.di.annotations.ApplicationContext;
import com.test.project24.di.modules.ActivityBuilder;
import com.test.project24.di.modules.ApplicationModule;
import com.test.project24.di.modules.BroadcastReceiverBuilder;
import com.test.project24.di.modules.FragmentBuildersModule;
import com.test.project24.di.modules.ServiceBuilder;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * @author goharali
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class
        , ServiceBuilder.class
        , ActivityBuilder.class
        , ApplicationModule.class
        , FragmentBuildersModule.class
        , BroadcastReceiverBuilder.class
})
public interface ApplicationComponent {


    /**
     * {@link Builder} with annotation {@link Component.Builder} is custom abstract class or interface
     * that is used to hold any instance.
     * So that we don't have to provide through this component's module in
     * this case {@link ApplicationModule}
     */
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(MyApp myApp);

        ApplicationComponent build();
    }


    void inject(MyApp myApp);

    @ApplicationContext
    Context context();

    IDataManager getDataManager();
}
