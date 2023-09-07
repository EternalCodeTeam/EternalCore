package com.eternalcode.core.metrics;

import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.Subscribe;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

class BStatsMetricsSetup {

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize(JavaPlugin javaPlugin) {
        Metrics metrics = new Metrics(javaPlugin, 13964);
        //metrics.addCustomChart(new SingleLineChart("users", () -> 0));
    }

}
