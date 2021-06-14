package com.example.arsitektur_mvp_and_greendao.ui.crud.insert;

import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

import java.util.List;

public interface InsertMvpView extends MvpView {
    void updateNumOfRecordInsert(Long numOfRecord);

    void updateInsertDatabaseTime(Long insertDatabaseTime);

    void updateAllInsertTime(Long allInsertTime);

    void updateViewInsertTime(Long viewInsertTime);

    void insertMedicalData(List<Medical> medicalList);

    void stateLoadingInsert(boolean state);
}
