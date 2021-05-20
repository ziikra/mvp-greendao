package com.example.arsitektur_mvp_and_greendao.data;

import com.example.arsitektur_mvp_and_greendao.data.db.DbHelper;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Hospital;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Medicine;

import java.util.List;

import io.reactivex.Flowable;

public interface DataManager extends DbHelper {

    Flowable<List<Hospital>> seedDatabaseHospital(Long numOfData);

    Flowable<Boolean> updateDatabaseHospital(Hospital hospital);

    Flowable<Boolean> deleteDatabaseHospital(Hospital hospital);

    Flowable<List<Medicine>> seedDatabaseMedicine(Long numOfData);

    Flowable<Boolean> updateDatabaseMedicine(Medicine medicine);

    Flowable<Boolean> deleteDatabaseMedicine(Medicine medicine);

}
