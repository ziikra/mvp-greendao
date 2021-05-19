package com.example.arsitektur_mvp_and_greendao.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

    Scheduler single();

    Scheduler trampoline();

    Scheduler fromA();

    Scheduler fromB();

    Scheduler fromC();

    Scheduler immediate();
}
