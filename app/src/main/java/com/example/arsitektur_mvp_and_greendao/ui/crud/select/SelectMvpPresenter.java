package com.example.arsitektur_mvp_and_greendao.ui.crud.select;

import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTimePreference;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpPresenter;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

public interface SelectMvpPresenter<V extends MvpView> extends MvpPresenter<V> {
    void onSelectExecuteClick(ExecutionTimePreference executionTimePreference, Long numOfData);
}
