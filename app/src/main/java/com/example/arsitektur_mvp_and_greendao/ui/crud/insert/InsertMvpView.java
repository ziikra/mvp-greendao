package com.example.arsitektur_mvp_and_greendao.ui.crud.insert;

import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

import java.util.List;

public interface InsertMvpView extends MvpView {
    void updateNumOfRecordInsert(Long numOfRecord);

    void updateExecutionTimeInsert(Long executionTime);

    void insertMedicalData(List<Medical> medicalList);

    void stateLoadingInsert(boolean state);
}
