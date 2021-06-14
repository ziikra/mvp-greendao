package com.example.arsitektur_mvp_and_greendao.ui.crud.delete;

import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

import java.util.List;

public interface DeleteMvpView extends MvpView {

    void updateNumOfRecordDelete(Long numOfRecord);

    void updateDeleteDatabaseTime(Long deleteDatabaseTime);

    void updateAllDeleteTime(Long allDeleteTime);

    void updateViewDeleteTime(Long viewDeleteTime);

    void deleteMedicalData(List<Medical> medicalList);

    void stateLoadingDelete(boolean state);
}
