package com.example.arsitektur_mvp_and_greendao.data.db;

import android.content.Context;
import android.util.Log;

import com.example.arsitektur_mvp_and_greendao.data.db.model.DaoMaster;
import com.example.arsitektur_mvp_and_greendao.di.ApplicationContext;
import com.example.arsitektur_mvp_and_greendao.di.DatabaseInfo;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {super(context, name);}

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion){
        super.onUpgrade(db, oldVersion, newVersion);
        Log.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion){
            case 1:
            case 2:
        }
    }
}
