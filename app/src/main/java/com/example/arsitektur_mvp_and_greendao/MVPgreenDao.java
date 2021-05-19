package com.example.arsitektur_mvp_and_greendao;

import android.app.Application;

import dagger.internal.DaggerCollections;

public class MVPgreenDao extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getmApplicationComponent() {
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent){
        mApplicationComponent = applicationComponent;
    }
}
