package com.example.arsitektur_mvp_and_greendao.data.db;

import com.example.arsitektur_mvp_and_greendao.data.db.model.DaoMaster;
import com.example.arsitektur_mvp_and_greendao.data.db.model.DaoSession;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Hospital;
import com.example.arsitektur_mvp_and_greendao.data.db.model.HospitalDao;
import com.example.arsitektur_mvp_and_greendao.data.db.model.Medicine;
import com.example.arsitektur_mvp_and_greendao.data.db.model.MedicineDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession daoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper){
        daoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }
    @Override
    public Flowable<Boolean> insertHospitals(List<Hospital> hospitals) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getHospitalDao().insertOrReplaceInTx(hospitals);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> insertMedicines(List<Medicine> medicines) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getMedicineDao().insertOrReplaceInTx(medicines);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> deleteHospitals(List<Hospital> hospitals) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getHospitalDao().deleteInTx(hospitals);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
    }


    @Override
    public Flowable<Boolean> deleteMedicines(List<Medicine> medicines) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getMedicineDao().deleteInTx(medicines);
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
    public Flowable<Boolean> saveHospitals(List<Hospital> hospitals) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getHospitalDao().insertOrReplaceInTx(hospitals);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
    }

    @Override
    public Flowable<Boolean> saveMedicines(List<Medicine> medicines) {
        return Flowable.fromCallable(() -> {
            try {
                daoSession.getMedicineDao().insertOrReplaceInTx(medicines);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
    }
}
