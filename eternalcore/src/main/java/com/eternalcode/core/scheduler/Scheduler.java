package com.eternalcode.core.scheduler;

import panda.std.reactive.Completable;

import java.time.Duration;
import java.util.function.Supplier;

public interface Scheduler {

    Task sync(Runnable task);

    Task async(Runnable task);

    Task laterSync(Runnable task, Duration delay);

    Task laterAsync(Runnable task, Duration delay);

    Task timerSync(Runnable task, Duration delay, Duration period);

    Task timerAsync(Runnable task, Duration delay, Duration period);

    <T> Completable<T> completeSync(Supplier<T> task);

    <T> Completable<T> completeAsync(Supplier<T> task);

}
