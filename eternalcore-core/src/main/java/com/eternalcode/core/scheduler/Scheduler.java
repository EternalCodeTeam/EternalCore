package com.eternalcode.core.scheduler;

import org.bukkit.scheduler.BukkitTask;
import panda.std.reactive.Completable;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Scheduler {

    Task sync(Runnable task);

    void sync(Consumer<BukkitTask> taskConsumer);

    Task async(Runnable task);

    void async(Consumer<BukkitTask> taskConsumer);

    Task laterSync(Runnable task, Duration delay);

    void laterSync(Consumer<BukkitTask> taskConsumer, Duration delay);

    Task laterAsync(Runnable task, Duration delay);

    void laterAsync(Consumer<BukkitTask> taskConsumer, Duration delay);

    Task timerSync(Runnable task, Duration delay, Duration period);

    void timerSync(Consumer<BukkitTask> taskConsumer, Duration delay, Duration period);

    Task timerAsync(Runnable task, Duration delay, Duration period);

    void timerAsync(Consumer<BukkitTask> taskConsumer, Duration delay, Duration period);

    <T> Completable<T> completeSync(Supplier<T> task);

    <T> Completable<T> completeAsync(Supplier<T> task);

}
