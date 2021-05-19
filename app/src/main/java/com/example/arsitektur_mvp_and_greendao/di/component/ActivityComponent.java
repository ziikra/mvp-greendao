package com.example.arsitektur_mvp_and_greendao.di.component;

import com.example.arsitektur_mvp_and_greendao.di.PerActivity;
import com.example.arsitektur_mvp_and_greendao.di.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public @interface ActivityComponent {

    void inject(CRUDActivity activity);

    void inject(InsertFragment fragment);

    void inject(SelectFragment fragment);

    void inject(UpdateFragment fragment);

    void inject(DeleteFragment fragment);
}
