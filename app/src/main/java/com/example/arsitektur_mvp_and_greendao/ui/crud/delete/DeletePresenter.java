package com.example.arsitektur_mvp_and_greendao.ui.crud.delete;

import android.util.Log;

import com.example.arsitektur_mvp_and_greendao.data.DataManager;
import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTime;
import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTimePreference;
import com.example.arsitektur_mvp_and_greendao.ui.base.BasePresenter;
import com.example.arsitektur_mvp_and_greendao.utils.rx.SchedulerProvider;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class DeletePresenter <V extends DeleteMvpView> extends BasePresenter<V> implements DeleteMvpPresenter<V> {

    private static final String TAG = "DeletePresenter";

    @Inject
    public DeletePresenter(DataManager mDataManager,
                           SchedulerProvider mSchedulerProvider,
                           CompositeDisposable mCompositeDisposable) {
        super(mDataManager, mSchedulerProvider, mCompositeDisposable);
    }

    public void deleteDatabase(ExecutionTimePreference executionTimePreference, Long numOfData) {
        AtomicLong viewDeleteTime = new AtomicLong(0);
        AtomicLong deleteDbTime = new AtomicLong(0);
        AtomicLong deleteTime = new AtomicLong(0);
        AtomicLong allDeleteTime = new AtomicLong(System.currentTimeMillis());
        AtomicInteger index = new AtomicInteger(0);
        getCompositeDisposable().add(getDataManager()
                //Get All Hospital with Limit
                .getAllHospital(numOfData >= 1000 ? numOfData / 1000 : 1)
                .concatMap(Flowable::fromIterable)
                //Get All Medicine with same hospital Id
                .concatMap(hospital -> Flowable.zip(
                        getDataManager().getMedicineForHospitalId(hospital.getId()),
                        Flowable.just(hospital),
                        ((medicineList, hospital1) -> medicineList)
                ))
                //Delete medicine name with object
                .concatMap(Flowable::fromIterable)
                .concatMap(medicine -> {
                    if (index.get() < numOfData) {
                        index.getAndIncrement();
                        deleteTime.set(System.currentTimeMillis());
                        return getDataManager().deleteDatabaseMedicine(medicine);
                    }
                    return Flowable.just(false);
                })
                .doOnNext(aBoolean -> {
                    if (aBoolean) {
                        deleteDbTime.set(deleteDbTime.longValue() + (System.currentTimeMillis() - deleteTime.longValue()));
                    }
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                            if (!isViewAttached())
                                return;
                            if (index.get() == numOfData) {
                                getMvpView().updateNumOfRecordDelete(index.longValue()); //Change number of record
                                getMvpView().updateDeleteDatabaseTime(deleteDbTime.longValue()); //Change execution time
                                AtomicLong endTime = new AtomicLong(System.currentTimeMillis());
                                AtomicLong timeElapsed = new AtomicLong(endTime.longValue() - allDeleteTime.longValue());
                                viewDeleteTime.set(timeElapsed.get() - deleteDbTime.longValue());
                                getMvpView().updateViewDeleteTime(viewDeleteTime.longValue());
                                getMvpView().updateAllDeleteTime(timeElapsed.longValue());

                                ExecutionTime executionTime = executionTimePreference.getExecutionTime();
                                executionTime.setDatabaseDeleteTime(deleteDbTime.toString());
                                executionTime.setAllDeleteTime(timeElapsed.toString());
                                executionTime.setViewDeleteTime(viewDeleteTime.toString());
                                executionTime.setNumOfRecordDelete(numOfData.toString());
                                executionTimePreference.setExecutionTime(executionTime);

                                Log.d(TAG, "deleteDatabase: " + index.get());
                                index.getAndIncrement();
                            }
                        }, throwable -> Log.d(TAG, "deleteDatabase: " + throwable.getMessage())
                )
        );
    }

    @Override
    public void onDeleteExecuteClick(ExecutionTimePreference executionTimePreference, Long numOfData) {
        deleteDatabase(executionTimePreference, numOfData);
    }
}
