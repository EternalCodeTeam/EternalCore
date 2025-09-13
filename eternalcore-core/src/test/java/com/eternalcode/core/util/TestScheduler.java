package com.eternalcode.core.util;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.commons.scheduler.Task;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class TestScheduler implements Scheduler {

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);

    public void shutdown() {
        this.executorService.shutdown();
    }

    @Override
    public Task run(Runnable runnable) {
        Future<?> future = this.executorService.submit(runnable);
        return new TestTask(future, false);
    }

    @Override
    public Task runAsync(Runnable runnable) {
        Future<?> future = CompletableFuture.runAsync(runnable, this.executorService);
        return new TestTask(future, false);
    }

    @Override
    public Task runLater(Runnable runnable, Duration duration) {
        ScheduledFuture<?> future = this.executorService.schedule(runnable, duration.toMillis(), TimeUnit.MILLISECONDS);
        return new TestTask(future, false);
    }

    @Override
    public Task runLaterAsync(Runnable runnable, Duration duration) {
        ScheduledFuture<?> future = this.executorService.schedule(() -> CompletableFuture.runAsync(runnable,
            this.executorService), duration.toMillis(), TimeUnit.MILLISECONDS);
        return new TestTask(future, false);
    }

    @Override
    public Task timer(Runnable runnable, Duration initialDelay, Duration period) {
        ScheduledFuture<?> future = this.executorService.scheduleAtFixedRate(runnable, initialDelay.toMillis(), period.toMillis(), TimeUnit.MILLISECONDS);
        return new TestTask(future, true);
    }

    @Override
    public Task timerAsync(Runnable runnable, Duration initialDelay, Duration period) {
        ScheduledFuture<?> future = this.executorService.scheduleAtFixedRate(() -> CompletableFuture.runAsync(runnable,
            this.executorService), initialDelay.toMillis(), period.toMillis(), TimeUnit.MILLISECONDS);
        return new TestTask(future, true);
    }

    @Override
    public <T> CompletableFuture<T> complete(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, this.executorService);
    }

    @Override
    public <T> CompletableFuture<T> completeAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, this.executorService);
    }

    private record TestTask(Future<?> future, boolean isRepeating) implements Task {

        @Override
        public void cancel() {
            this.future.cancel(false);
        }

        @Override
        public boolean isCanceled() {
            return this.future.isCancelled();
        }

        @Override
        public boolean isAsync() {
            return this.future instanceof CompletableFuture || this.future instanceof ScheduledFuture;
        }

        @Override
        public boolean isRunning() {
            return !this.future.isDone();
        }
    }
}
