package com.test.project24.utils.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TrampolineSchedulerProvider implements SchedulerProvider {
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler Queue() {
        return Schedulers.trampoline();
    }
}
