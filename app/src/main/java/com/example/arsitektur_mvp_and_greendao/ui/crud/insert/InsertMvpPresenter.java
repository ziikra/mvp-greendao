package com.example.arsitektur_mvp_and_greendao.ui.crud.insert;

import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTimePreference;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpPresenter;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

public interface InsertMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    void onInsertExecuteClick(ExecutionTimePreference executionTimePreference, Long numOfData);

}
