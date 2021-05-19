package com.example.arsitektur_mvp_and_greendao.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import javax.inject.Inject;

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

    @Generated()
    public Hospital(Long id, String name){
        this.id = id;
        this.name = name;
    }

    @Generated
    public Hospital(){

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

    public List<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }
}
