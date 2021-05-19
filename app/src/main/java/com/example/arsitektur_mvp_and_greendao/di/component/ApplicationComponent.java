package com.example.arsitektur_mvp_and_greendao.di.component;

import android.app.Application;
import android.content.Context;

import com.example.arsitektur_mvp_and_greendao.MVPgreenDao;
import com.example.arsitektur_mvp_and_greendao.data.DataManager;
import com.example.arsitektur_mvp_and_greendao.di.ApplicationContext;
import com.example.arsitektur_mvp_and_greendao.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public @interface ApplicationComponent {

    void inject(MVPgreenDao app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();

}
