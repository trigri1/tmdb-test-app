package com.test.project24.utils.rx;

import io.reactivex.Scheduler;

/**
 * @author goharali
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

    Scheduler Queue();

}
