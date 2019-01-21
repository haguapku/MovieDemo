package com.example.moviedemo.util;

import io.reactivex.Scheduler;

/**
 * Author: created by MarkYoung on 18/01/2019 15:15
 */
public interface SchedulerProvider {

    Scheduler ui();
    Scheduler computation();
    Scheduler trampoline();
    Scheduler newThread();
    Scheduler io();

}
