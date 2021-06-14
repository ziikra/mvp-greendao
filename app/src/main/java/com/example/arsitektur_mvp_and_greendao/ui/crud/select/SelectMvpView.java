package com.example.arsitektur_mvp_and_greendao.ui.crud.select;

import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

import java.util.List;

public interface SelectMvpView extends MvpView {
    void updateNumOfRecordSelect(Long numOfRecord);

    void updateSelectDatabaseTime(Long selectDatabaseTime);

    void updateAllSelectTime(Long allSelectTime);

    void updateViewSelectTime(Long viewSelectTime);

    void selectMedicalData(List<Medical> medicalList);

    void stateLoadingSelect(boolean state);


}
