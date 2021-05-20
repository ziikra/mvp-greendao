package com.example.arsitektur_mvp_and_greendao.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "hospitals")
public class Hospital {

    @Expose
    @SerializedName("id")
    @Id
    private Long id;

    @Expose
    @SerializedName("hospitalName")
    @Property(nameInDb = "name")
    private String name;

    @ToMany(referencedJoinProperty = "hospitalId")
    private List<Medicine> medicineList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 679648518)
    private transient HospitalDao myDao;

    @Generated(hash = 714822730)
    public Hospital(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1301721268)
    public Hospital() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2131197227)
    public List<Medicine> getMedicineList() {
        if (medicineList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MedicineDao targetDao = daoSession.getMedicineDao();
            List<Medicine> medicineListNew = targetDao
                    ._queryHospital_MedicineList(id);
            synchronized (this) {
                if (medicineList == null) {
                    medicineList = medicineListNew;
                }
            }
        }
        return medicineList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1205167936)
    public synchronized void resetMedicineList() {
        medicineList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 267021903)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHospitalDao() : null;
    }

}
