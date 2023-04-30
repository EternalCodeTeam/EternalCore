package com.eternalcode.core.scheduler;

import panda.std.reactive.Completable;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Scheduler {

    Task sync(Runnable task);

    void sync(Consumer<Task> taskConsumer);

    Task async(Runnable task);

    void async(Consumer<Task> taskConsumer);

    Task laterSync(Runnable task, Duration delay);

    void laterSync(Consumer<Task> taskConsumer, Duration delay);

    Task laterAsync(Runnable task, Duration delay);

    void laterAsync(Consumer<Task> taskConsumer, Duration delay);

    Task timerSync(Runnable task, Duration delay, Duration period);

    void timerSync(Consumer<Task> taskConsumer, Duration delay, Duration period);

    Task timerAsync(Runnable task, Duration delay, Duration period);

    void timerAsync(Consumer<Task> taskConsumer, Duration delay, Duration period);

    <T> Completable<T> completeSync(Supplier<T> task);

    <T> Completable<T> completeAsync(Supplier<T> task);

}
