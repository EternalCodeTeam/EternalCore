package com.eternalcode.core.metrics;

import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.Subscribe;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

@Controller
class BStatsMetricsSetup {

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize(JavaPlugin javaPlugin) {
        Metrics metrics = new Metrics(javaPlugin, 13964);
    }

}
