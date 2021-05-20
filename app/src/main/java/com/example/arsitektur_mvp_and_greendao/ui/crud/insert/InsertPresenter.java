package com.example.arsitektur_mvp_and_greendao.ui.crud.insert;

import android.util.Log;

import com.example.arsitektur_mvp_and_greendao.data.DataManager;
import com.example.arsitektur_mvp_and_greendao.ui.base.BasePresenter;
import com.example.arsitektur_mvp_and_greendao.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class InsertPresenter<V extends InsertMvpView> extends BasePresenter<V> implements InsertMvpPresenter<V>{

    private static final String TAG = "InsertPresenter";

    @Inject
    public InsertPresenter(DataManager mDataManager,
                           SchedulerProvider mSchedulerProvider,
                           CompositeDisposable mCompositeDisposable) {
        super(mDataManager, mSchedulerProvider, mCompositeDisposable);
    }

    public void insertDatabase(Long numOfData) {
        long startTime = System.currentTimeMillis();
        //Insert Hospital JSON to DB
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseHospital(numOfData)
                .concatMap(Flowable::fromIterable)
                .concatMap(hospital -> getDataManager().insertHospital(hospital))
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                        } , throwable -> getMvpView().onError(throwable.getMessage())
                )
        );
        //Insert Medicine JSON to DB
        getCompositeDisposable().add(getDataManager()
                .seedDatabaseMedicine(numOfData)
                .concatMap(Flowable::fromIterable)
                .concatMap(medicine -> getDataManager().insertMedicine(medicine))
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                            if (!isViewAttached())
                                return;
                            if (aBoolean) {
                                getMvpView().updateNumOfRecordInsert(numOfData);
                                long endTime = System.currentTimeMillis();
                                long timeElapsed = endTime - startTime; //In MilliSeconds
                                getMvpView().updateExecutionTimeInsert(timeElapsed);
                            }
                        } , throwable -> Log.d(TAG, "insertDatabase: " + throwable.getMessage())
                )
        );
    }

    @Override
    public void onInsertExecuteClick(Long numOfData) {
        insertDatabase(numOfData);
    }
}
