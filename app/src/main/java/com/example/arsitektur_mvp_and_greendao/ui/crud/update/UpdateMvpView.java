package com.example.arsitektur_mvp_and_greendao.ui.crud.update;

import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

import java.util.List;

public interface UpdateMvpView extends MvpView {
    void updateNumOfRecordUpdate(Long numOfRecord);

    void updateUpdateDatabaseTime(Long updateDatabaseTime);

    void updateAllUpdateTime(Long allUpdateTime);

    void updateViewUpdateTime(Long viewUpdateTime);

    void updateMedicalData(List<Medical> medicalList);

    void stateLoadingUpdate(boolean state);
}
