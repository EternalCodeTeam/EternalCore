package com.eternalcode.core.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import panda.std.reactive.Completable;

import java.time.Duration;
import java.util.function.Supplier;

public class BukkitSchedulerImpl implements Scheduler {

    private final Plugin plugin;
    private final BukkitScheduler rootScheduler;

    public BukkitSchedulerImpl(Plugin plugin) {
        this.plugin = plugin;
        this.rootScheduler = plugin.getServer().getScheduler();
    }

    @Override
    public void runTaskTimer(Runnable task, long delay, long period) {
        this.rootScheduler.runTaskTimer(this.plugin, task, delay, period);
    }

    @Override
    public Task sync(Runnable task) {
        return new BukkitTaskImpl(this.rootScheduler.runTask(this.plugin, task));
    }

    @Override
    public Task async(Runnable task) {
        return new BukkitTaskImpl(this.rootScheduler.runTaskAsynchronously(this.plugin, task));
    }

    @Override
    public Task laterSync(Runnable task, Duration delay) {
        return new BukkitTaskImpl(this.rootScheduler.runTaskLater(this.plugin, task, toTick(delay)));
    }

    @Override
    public Task laterAsync(Runnable task, Duration delay) {
        return new BukkitTaskImpl(this.rootScheduler.runTaskLaterAsynchronously(this.plugin, task, toTick(delay)));
    }

    @Override
    public Task timerSync(Runnable task, Duration delay, Duration period) {
        return new BukkitTaskImpl(this.rootScheduler.runTaskTimer(this.plugin, task, toTick(delay), toTick(period)));
    }

    @Override
    public Task timerAsync(Runnable task, Duration delay, Duration period) {
        return new BukkitTaskImpl(this.rootScheduler.runTaskTimerAsynchronously(this.plugin, task, toTick(delay), toTick(period)));
    }

    @Override
    public <T> Completable<T> completeSync(Supplier<T> task) {
        Completable<T> completable = new Completable<>();
        this.rootScheduler.runTask(this.plugin, () -> completable.complete(task.get()));
        return completable;
    }

    @Override
    public <T> Completable<T> completeAsync(Supplier<T> task) {
        Completable<T> completable = new Completable<>();
        this.rootScheduler.runTaskAsynchronously(this.plugin, () -> completable.complete(task.get()));
        return completable;
    }

    private long toTick(Duration duration) {
        return duration.toMillis() / 50L;
    }

}
