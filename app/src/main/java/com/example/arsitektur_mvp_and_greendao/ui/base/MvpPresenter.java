package com.example.arsitektur_mvp_and_greendao.ui.base;

public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);

    void onDetach();
}
