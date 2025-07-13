package com.eternalcode.core.scheduler;

import com.eternalcode.commons.bukkit.scheduler.BukkitSchedulerImpl;
import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.commons.folia.scheduler.FoliaSchedulerImpl;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import org.bukkit.plugin.Plugin;

@Setup
class SchedulerSetup {

    @Bean
    MinecraftScheduler scheduler(Plugin plugin) {
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler");
            Class.forName("io.papermc.paper.threadedregions.scheduler.RegionScheduler");
            Class.forName("io.papermc.paper.threadedregions.scheduler.AsyncScheduler");
            return new FoliaSchedulerImpl(plugin);
        } catch (ClassNotFoundException notFoundException) {
            return new BukkitSchedulerImpl(plugin);
        }
    }

}
