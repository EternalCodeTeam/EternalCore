package com.eternalcode.core.metrics;

import com.eternalcode.core.event.EternalCoreInitializeEvent;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

@Controller
public class BStatsMetricsSetup implements Subscriber {

    @Subscribe
    public void onInitialize(EternalCoreInitializeEvent event, JavaPlugin javaPlugin) {
        Metrics metrics = new Metrics(javaPlugin, 13964);
        //metrics.addCustomChart(new SingleLineChart("users", () -> 0));
    }

}
