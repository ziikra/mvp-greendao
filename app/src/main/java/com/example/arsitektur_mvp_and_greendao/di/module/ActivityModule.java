package com.example.arsitektur_mvp_and_greendao.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.arsitektur_mvp_and_greendao.di.ActivityContext;
import com.example.arsitektur_mvp_and_greendao.ui.crud.CRUDPagerAdapter;
import com.example.arsitektur_mvp_and_greendao.ui.crud.delete.DeleteAdapter;
import com.example.arsitektur_mvp_and_greendao.ui.crud.delete.DeleteMvpView;
import com.example.arsitektur_mvp_and_greendao.ui.crud.delete.DeletePresenter;
import com.example.arsitektur_mvp_and_greendao.ui.crud.insert.InsertAdapter;
import com.example.arsitektur_mvp_and_greendao.ui.crud.insert.InsertMvpView;
import com.example.arsitektur_mvp_and_greendao.ui.crud.insert.InsertPresenter;
import com.example.arsitektur_mvp_and_greendao.ui.crud.select.SelectAdapter;
import com.example.arsitektur_mvp_and_greendao.ui.crud.select.SelectMvpView;
import com.example.arsitektur_mvp_and_greendao.ui.crud.select.SelectPresenter;
import com.example.arsitektur_mvp_and_greendao.ui.crud.update.UpdateAdapter;
import com.example.arsitektur_mvp_and_greendao.ui.crud.update.UpdateMvpView;
import com.example.arsitektur_mvp_and_greendao.ui.crud.update.UpdatePresenter;
import com.example.arsitektur_mvp_and_greendao.utils.rx.AppSchedulerProvider;
import com.example.arsitektur_mvp_and_greendao.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity){
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity(){
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider(){
        return new AppSchedulerProvider();
    }

    @Provides
    @Named("insertPresenter")
    InsertPresenter<InsertMvpView> provideInsertPresenter(
            InsertPresenter<InsertMvpView> presenter){
        return presenter;
    }

    @Provides
    @Named("selectPresenter")
    SelectPresenter<SelectMvpView> provideSelectPresenter(
            SelectPresenter<SelectMvpView> presenter){
        return presenter;
    }

    @Provides
    @Named("updatePresenter")
    UpdatePresenter<UpdateMvpView> provideUpdatePresenter(
            UpdatePresenter<UpdateMvpView> presenter) {
        return presenter;
    }

    @Provides
    @Named("deletePresenter")
    DeletePresenter<DeleteMvpView> provideDeletePresenter(
            DeletePresenter<DeleteMvpView> presenter){
        return presenter;
    }

    @Provides
    CRUDPagerAdapter provideCrudPagerAdapter(AppCompatActivity activity) {
        return new CRUDPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    @Named("insertAdapter")
    InsertAdapter provideInsertAdapter(){
        return new InsertAdapter(new ArrayList<>());
    }

    @Provides
    @Named("selectAdapter")
    SelectAdapter provideSelectAdapter(){return new SelectAdapter(new ArrayList<>());
    }

    @Provides
    @Named("updateAdapter")
    UpdateAdapter provideUpdateAdapter() {
        return new UpdateAdapter(new ArrayList<>());
    }

    @Provides
    @Named("deleteAdapter")
    DeleteAdapter provideDeleteAdapter() {
        return new DeleteAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
