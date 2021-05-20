package com.example.arsitektur_mvp_and_greendao.di.module;

import android.app.Application;
import android.content.Context;

import com.example.arsitektur_mvp_and_greendao.data.AppDataManager;
import com.example.arsitektur_mvp_and_greendao.data.DataManager;
import com.example.arsitektur_mvp_and_greendao.data.db.AppDbHelper;
import com.example.arsitektur_mvp_and_greendao.data.db.DbHelper;
import com.example.arsitektur_mvp_and_greendao.di.ApplicationContext;
import com.example.arsitektur_mvp_and_greendao.di.DatabaseInfo;
import com.example.arsitektur_mvp_and_greendao.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application mApplication){
        this.mApplication =  mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

}
