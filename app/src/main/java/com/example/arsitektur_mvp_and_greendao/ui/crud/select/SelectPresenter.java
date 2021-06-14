package com.example.arsitektur_mvp_and_greendao.ui.crud.select;

import android.util.Log;

import com.example.arsitektur_mvp_and_greendao.data.DataManager;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Medicine;
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

    @Inject
    public SelectPresenter(DataManager mDataManager,
                           SchedulerProvider mSchedulerProvider,
                           CompositeDisposable mCompositeDisposable) {
        super(mDataManager, mSchedulerProvider, mCompositeDisposable);
    }

    public void selectDatabase(Long numOfData) {
        AtomicLong viewSelectTime = new AtomicLong(0);
        AtomicLong selectDbTime = new AtomicLong(0);
        AtomicLong selectTime = new AtomicLong(0);
        AtomicLong allSelectTime = new AtomicLong(System.currentTimeMillis());
        AtomicInteger index = new AtomicInteger(0);
        List<Medical> medicals = new ArrayList<>();
        getCompositeDisposable().add(getDataManager()
                //Get All Hospital with Limit
                .getAllHospital(numOfData >= 1000 ? numOfData / 1000 : 1)
                .concatMap(Flowable::fromIterable)
                //Get All Medicine with same hospital Id
                .concatMap(hospital -> {
                    selectTime.set(System.currentTimeMillis());
                    return Flowable.zip(
                            getDataManager().getMedicineForHospitalId(hospital.getId()),
                            Flowable.just(hospital),
                            ((medicineList, h) -> {

                                for (Medicine m : medicineList) {
                                    if (index.get() < numOfData) {
                                        medicals.add(new Medical(h.getName(), m.getName()));
                                        index.getAndIncrement();
                                    }
                                }
                                return medicals;
                            })
                    );
                })
                .doOnNext(medicalList -> {
                    if (!medicalList.isEmpty())
                        selectDbTime.set(selectDbTime.longValue() + (System.currentTimeMillis() - selectTime.longValue()));
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(medicalList -> {
                    if (!isViewAttached())
                        return;
                    if (medicalList != null && index.get() == numOfData) {
                        getMvpView().selectMedicalData(medicalList); //Change data list
                        getMvpView().updateNumOfRecordSelect(index.longValue());
                        getMvpView().updateSelectDatabaseTime(selectDbTime.longValue()); //Change execution time
                        AtomicLong endTime = new AtomicLong(System.currentTimeMillis());
                        AtomicLong timeElapsed = new AtomicLong(endTime.longValue() - allSelectTime.longValue());
                        viewSelectTime.set(timeElapsed.get() - selectDbTime.longValue());
                        getMvpView().updateViewSelectTime(viewSelectTime.longValue());
                        getMvpView().updateAllSelectTime(timeElapsed.longValue());
                        Log.d(TAG, "selectDatabase: " + index.get());
                        index.getAndIncrement();
                    }
                }, throwable -> Log.d(TAG, "selectDatabase: " + throwable.getMessage()))
        );
    }

    @Override
    public void onSelectExecuteClick(Long numOfData){
        selectDatabase(numOfData);
    }
}