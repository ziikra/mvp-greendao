package com.example.arsitektur_mvp_and_greendao.ui.base;

public interface SubMvpView {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attachParentMvpView(MvpView mvpView);

}
