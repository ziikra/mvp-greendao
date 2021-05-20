package com.example.arsitektur_mvp_and_greendao;

import android.app.Application;

import com.example.arsitektur_mvp_and_greendao.data.DataManager;
import com.example.arsitektur_mvp_and_greendao.di.component.ApplicationComponent;

import com.example.arsitektur_mvp_and_greendao.di.component.DaggerApplicationComponent;
import com.example.arsitektur_mvp_and_greendao.di.module.ApplicationModule;

import javax.inject.Inject;

public class MVPgreenDao extends Application {

    @Inject
    DataManager dataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent){
        mApplicationComponent = applicationComponent;
    }
}
