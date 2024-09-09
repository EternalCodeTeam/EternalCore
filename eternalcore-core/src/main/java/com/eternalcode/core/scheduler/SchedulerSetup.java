package com.eternalcode.core.scheduler;

import com.eternalcode.commons.bukkit.scheduler.BukkitSchedulerImpl;
import com.eternalcode.commons.folia.scheduler.FoliaSchedulerImpl;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscriber;
import org.bukkit.plugin.Plugin;

@BeanSetup
public class SchedulerSetup implements Subscriber {

    public static final String FOLIA = "io.papermc.paper.threadedregions.RegionizedServer";

    @Bean
    public Scheduler scheduler(Plugin plugin) {
        return getAdaptiveScheduler(plugin);
    }

    public static Scheduler getAdaptiveScheduler(Plugin plugin) {
        if (isFolia()) {
            return new FoliaSchedulerImpl(plugin);
        }
        else {
            return new BukkitSchedulerImpl(plugin);
        }
    }

    private static boolean isFolia() {
        try {
            Class.forName(FOLIA);
            return true;
        }
        catch (ClassNotFoundException exception) {
            return false;
        }
    }
}
