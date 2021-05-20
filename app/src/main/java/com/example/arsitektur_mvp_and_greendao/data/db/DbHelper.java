package com.example.arsitektur_mvp_and_greendao.data.db;

import androidx.constraintlayout.helper.widget.Flow;

import com.example.arsitektur_mvp_and_greendao.data.db.model.Hospital;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Medicine;

import java.util.List;

import io.reactivex.Flowable;

public interface DbHelper {

    Flowable<Boolean> insertHospital(Hospital hospital);

    Flowable<Boolean> insertMedicine(Medicine medicine);

    Flowable<Boolean> deleteHospital(Hospital hospital);

    Flowable<Boolean> deleteMedicine(Medicine medicine);

    Flowable<Hospital> loadHospital(Hospital hospital);

    Flowable<Medicine> loadMedicine(Medicine medicine);

    Flowable<List<Hospital>> getAllHospital();

    Flowable<List<Hospital>> getAllHospital(Long numOfData);

    Flowable<List<Medicine>> getAllMedicine();

    Flowable<List<Medicine>> getAllMedicine(Long numOfData);

    Flowable<List<Medicine>> getMedicineForHospitalId(Long hospitalId);

    Flowable<Boolean> saveHospital(Hospital hospital);

    Flowable<Boolean> saveMedicine(Medicine medicine);
}
