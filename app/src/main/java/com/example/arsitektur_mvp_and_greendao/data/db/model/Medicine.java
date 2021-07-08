package com.example.arsitektur_mvp_and_greendao.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

// Model dari Object Medicine
@Entity(nameInDb = "medicines")
public class Medicine {

    // Attribute id berupa integer (long)
    @Expose
    @SerializedName("id") //Pemrosesan konversi file json ke dalam objek
    @Id //Annotation untuk menyatakan attribut ini sebagai id atau primary key
    private Long id;

    @Expose
    @SerializedName("hospitalId")
    @Property(nameInDb = "hospitalId")  //Annotation untuk menyatakan attribut ini merupakan foreign key
    private Long hospitalId;

    @Expose
    @SerializedName("medicineName")
    @Property(nameInDb = "name")        //Annotation untuk menyatakan attribut ini merupakan property
    private String name;

    @Generated(hash = 1489536452)
    public Medicine(Long id, Long hospitalId, String name) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.name = name;
    }

    @Generated(hash = 1065091254)
    public Medicine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
