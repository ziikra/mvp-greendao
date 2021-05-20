package com.example.arsitektur_mvp_and_greendao.ui.crud.update;

import com.example.arsitektur_mvp_and_greendao.ui.base.MvpPresenter;
import com.example.arsitektur_mvp_and_greendao.ui.base.MvpView;

public interface UpdateMvpPresenter<V extends MvpView> extends MvpPresenter<V> {
    void onUpdateExecuteClick(Long numOfData);
}
