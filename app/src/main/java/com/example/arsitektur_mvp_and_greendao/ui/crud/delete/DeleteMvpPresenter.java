package com.example.arsitektur_mvp_and_greendao.ui.crud.delete;

import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTimePreference;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpPresenter;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

public interface DeleteMvpPresenter<V extends MvpView> extends MvpPresenter<V> {
    void onDeleteExecuteClick(ExecutionTimePreference executionTimePreference, Long numOfData);
}
