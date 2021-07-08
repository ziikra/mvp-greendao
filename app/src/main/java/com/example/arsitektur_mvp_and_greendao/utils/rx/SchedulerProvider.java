package com.example.arsitektur_mvp_and_greendao.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler ui();

    Scheduler io();
}
