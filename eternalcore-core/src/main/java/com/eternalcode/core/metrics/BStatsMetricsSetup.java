package com.eternalcode.core.metrics;

import com.eternalcode.core.event.EternalCoreInitializeEvent;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

@Subscriber
class BStatsMetricsSetup {

    @Subscribe(EternalCoreInitializeEvent.class)
    public void onInitialize(JavaPlugin javaPlugin) {
        Metrics metrics = new Metrics(javaPlugin, 13964);
        //metrics.addCustomChart(new SingleLineChart("users", () -> 0));
    }

}
