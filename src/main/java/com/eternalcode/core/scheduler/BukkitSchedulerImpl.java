package com.eternalcode.core.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class BukkitSchedulerImpl implements Scheduler {

    private final Plugin plugin;
    private final BukkitScheduler rootScheduler;

    public BukkitSchedulerImpl(Plugin plugin) {
        this.plugin = plugin;
        this.rootScheduler = plugin.getServer().getScheduler();
    }

    @Override
    public void runTask(Runnable task) {
        this.rootScheduler.runTask(this.plugin, task);
    }

    @Override
    public void runTaskAsynchronously(Runnable task) {
        this.rootScheduler.runTaskAsynchronously(this.plugin, task);
    }

    @Override
    public void runTaskLater(Runnable task, long after) {
        this.rootScheduler.runTaskLater(this.plugin, task, after);
    }

    @Override
    public void runTaskLaterAsynchronously(Runnable task, long after) {
        this.rootScheduler.runTaskLaterAsynchronously(this.plugin, task, after);
    }

    @Override
    public void runTaskTimer(Runnable task, long delay, long period) {
        this.rootScheduler.runTaskTimer(this.plugin, task, delay, period);
    }

    @Override
    public void runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        this.rootScheduler.runTaskTimerAsynchronously(this.plugin, task, delay, period);
    }

}
