package com.example.arsitektur_mvp_and_greendao.ui.base;

import com.example.arsitektur_mvp_and_greendao.data.DataManager;
import com.example.arsitektur_mvp_and_greendao.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private static final String TAG = "BasePresenter";

    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    @Inject
    public BasePresenter(DataManager mDataManager,
                         SchedulerProvider mSchedulerProvider,
                         CompositeDisposable mCompositeDisposable) {
        this.mDataManager = mDataManager;
        this.mSchedulerProvider = mSchedulerProvider;
        this.mCompositeDisposable = mCompositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        this.mCompositeDisposable.dispose();
        this.mMvpView = null;
    }

    public boolean isViewAttached() {
        return this.mMvpView != null;
    }

    public V getMvpView() {
        return this.mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return this.mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return this.mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return this.mCompositeDisposable;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
