package com.example.arsitektur_mvp_and_greendao.ui.crud.select;

import android.util.Log;

import com.example.arsitektur_mvp_and_greendao.data.DataManager;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Hospital;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Medicine;
import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTime;
import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTimePreference;
import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.ui.base.BasePresenter;
import com.example.arsitektur_mvp_and_greendao.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class SelectPresenter<V extends SelectMvpView> extends BasePresenter<V> implements SelectMvpPresenter<V> {

    private static final String TAG = "SelectPresenter";

    @Inject // Penggunaan dependency injection pada instance select presenter
    public SelectPresenter(DataManager mDataManager,
                           SchedulerProvider mSchedulerProvider,
                           CompositeDisposable mCompositeDisposable) {
        super(mDataManager, mSchedulerProvider, mCompositeDisposable);
    }

    // Method yang digunakan untuk select data
    public void selectDatabase(ExecutionTimePreference executionTimePreference, Long numOfData) {
        AtomicLong viewSelectTime = new AtomicLong(0);
        AtomicLong selectDbTime = new AtomicLong(0);
        AtomicLong selectTime = new AtomicLong(0);
        AtomicLong allSelectTime = new AtomicLong(System.currentTimeMillis());
        AtomicInteger indexAdd = new AtomicInteger(0);
        AtomicInteger total = new AtomicInteger(0);
        List<Medical> medicals = new ArrayList<>();
        List<Hospital> hospitals = new ArrayList<>();
        List<Medicine> medicines = new ArrayList<>();

        getCompositeDisposable().add(getDataManager()
            .getAllHospital(numOfData >= 1000 ? numOfData / 1000 : 1)
                .concatMap(Flowable::fromIterable)
                .concatMap(hospital -> {
                    if (hospital != null) {
                        total.set(total.intValue() + 1000);
                        selectTime.set(System.currentTimeMillis());
                        return getDataManager().loadHospital(hospital);
                    }
                    return null;
                })
                .doOnNext(hospital -> {
                    if (hospital != null) {
                        selectDbTime.set(selectDbTime.longValue()
                                + (System.currentTimeMillis() - selectTime.longValue()));
                        while (indexAdd.longValue() < total.longValue()) {
                            hospitals.add(indexAdd.intValue(), hospital);
                            indexAdd.getAndIncrement();
                        }
                    }
                })
            .observeOn(getSchedulerProvider().ui())
            .subscribe(hospital -> {
            }, throwable -> Log.d("SVM", "selectDatabase 1: " + throwable.getMessage())));
        indexAdd.set(0);
        getCompositeDisposable().add(getDataManager()
            .getAllMedicine(numOfData)
                .concatMap(Flowable::fromIterable)
                .concatMap(medicine -> {
                    if (medicine != null) {
                        selectTime.set(System.currentTimeMillis());
                        return getDataManager().loadMedicine(medicine);
                    }
                    return null;
                })
                .doOnNext(medicine -> {
                    if (medicine != null) {
                        selectDbTime.set(selectDbTime.longValue()
                                + (System.currentTimeMillis() - selectTime.longValue()));
                        if (indexAdd.longValue() < numOfData) {
                            medicines.add(indexAdd.intValue(), medicine);
                            indexAdd.getAndIncrement();
                        }
                    }
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(medicine -> {
                    if (!isViewAttached())
                        return;
                    if (indexAdd.longValue() == numOfData) {
                        for (int i = 0; i < numOfData; i++) {
                            medicals.add(new Medical(hospitals.get(i).getName(), medicines.get(i).getName()));
                        }
                        getMvpView().selectMedicalData(medicals); //Change data list
                        getMvpView().updateNumOfRecordSelect(indexAdd.longValue());
                        getMvpView().updateSelectDatabaseTime(selectDbTime.longValue()); //Change execution time
                        AtomicLong endTime = new AtomicLong(System.currentTimeMillis());
                        AtomicLong timeElapsed = new AtomicLong(endTime.longValue() - allSelectTime.longValue());
                        viewSelectTime.set(timeElapsed.get() - selectDbTime.longValue());
                        getMvpView().updateViewSelectTime(viewSelectTime.longValue());
                        getMvpView().updateAllSelectTime(timeElapsed.longValue());

                        ExecutionTime executionTime = executionTimePreference.getExecutionTime();
                        executionTime.setDatabaseSelectTime(selectDbTime.toString());
                        executionTime.setAllSelectTime(timeElapsed.toString());
                        executionTime.setViewSelectTime(viewSelectTime.toString());
                        executionTime.setNumOfRecordSelect(numOfData.toString());
                        executionTimePreference.setExecutionTime(executionTime);

                        indexAdd.getAndIncrement();
                    }
                })
        );
    }

    @Override
    public void onSelectExecuteClick(ExecutionTimePreference executionTimePreference, Long numOfData) {
        selectDatabase(executionTimePreference, numOfData);
    }
}
