package com.example.arsitektur_mvp_and_greendao.data.db;

import androidx.constraintlayout.helper.widget.Flow;

import com.example.arsitektur_mvp_and_greendao.data.db.DbHelper;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Hospital;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Medicine;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class AppDbHelper implements DbHelper {

    private final DaoSession daoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper){
        daoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }
    @Override
    public Flowable<Boolean> insertHospital(Hospital hospital) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getHospitaldDao().insertOrReplace(hospital);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> insertMedicine(Medicine medicine) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getMedicineDao().insertOrReplace(medicine);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> deleteHospital(Hospital hospital) {
        return Flowable.fromCallable(() -> {
            try {
                final Hospital unique = daoSession.getHospitalDao().queryBuilder()
                        .where(HospitalDao.Properties.Id.eq(hospital.getId())).unique();
                daoSession.getHospitalDao().deleteInTx(unique);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
    }


    @Override
    public Flowable<Boolean> deleteMedicine(Medicine medicine) {
        return Flowable.fromCallable(() -> {
            try {
                final Medicine unique = daoSession.getMedicineDao().queryBuilder()
                        .where(MedicineDao.Properties.Id.eq(medicine.getId())).unique();
                daoSession.getMedicineDao().deleteInTx(unique);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Flowable<Hospital> loadHospital(Hospital hospital) {
        return Flowable.fromCallable(() -> {
            try {
                return daoSession.getHospitalDao().queryBuilder()
                        .where(HospitalDao.Properties.Id.eq(hospital.getId())).unique();
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    public Flowable<Medicine> loadMedicine(Medicine medicine) {
        return Flowable.fromCallable(() -> {
            try {
                return daoSession.getMedicineDao().queryBuilder()
                        .where(MedicineDao.Properties.Id.eq(medicine.getId())).unique();
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        });
    }

    @Override
    public Flowable<List<Hospital>> getAllHospital() {
        return Flowable.fromCallable(() -> daoSession.getHospitalDao().loadAll());
    }

    @Override
    public Flowable<List<Hospital>> getAllHospital(Long numOfData) {
        return Flowable.fromCallable(() -> daoSession.getHospitalDao()
        .queryBuilder().limit(numOfData.intValue())
        .list());
    }

    @Override
    public Flowable<List<Medicine>> getAllMedicine() {
        return Flowable.fromCallable(() -> daoSession.getMedicineDao().loadAll());
    }

    @Override
    public Flowable<List<Medicine>> getAllMedicine(Long numOfData) {
        return Flowable.fromCallable(() -> daoSession.getMedicineDao().queryBuilder()
        .limit(numOfData.intValue())
        .list());
    }

    @Override
    public Flowable<List<Medicine>> getMedicineForHospitalId(Long hospitalId) {
        return Flowable.fromCallable(() -> daoSession.getMedicineDao()._queryHospital_MedicineList(hospitalId));
    }

    @Override
    public Flowable<Boolean> saveHospital(Hospital hospital) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getHospitalDao().insertOrReplaceInTx(hospital);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> saveMedicine(Medicine medicine) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getMedicineDao().insertOrReplaceInTx(medicine);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
    }
}
