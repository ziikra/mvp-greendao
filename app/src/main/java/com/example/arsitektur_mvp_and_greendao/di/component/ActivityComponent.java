package com.example.arsitektur_mvp_and_greendao.di.component;

import com.example.arsitektur_mvp_and_greendao.di.PerActivity;
import com.example.arsitektur_mvp_and_greendao.di.module.ActivityModule;
import com.example.arsitektur_mvp_and_greendao.ui.crud.CRUDActivity;
import com.example.arsitektur_mvp_and_greendao.ui.crud.delete.DeleteFragment;
import com.example.arsitektur_mvp_and_greendao.ui.crud.insert.InsertFragment;
import com.example.arsitektur_mvp_and_greendao.ui.crud.select.SelectFragment;
import com.example.arsitektur_mvp_and_greendao.ui.crud.update.UpdateFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(CRUDActivity activity);

    void inject(InsertFragment fragment);

    void inject(SelectFragment fragment);

    void inject(UpdateFragment fragment);

    void inject(DeleteFragment fragment);
}
