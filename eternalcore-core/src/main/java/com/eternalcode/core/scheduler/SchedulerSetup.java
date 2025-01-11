package com.eternalcode.core.scheduler;

import com.eternalcode.commons.bukkit.scheduler.BukkitSchedulerImpl;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import org.bukkit.plugin.Plugin;

@Setup
public class SchedulerSetup {

    @Bean
    public Scheduler scheduler(Plugin plugin) {
        return new BukkitSchedulerImpl(plugin);
    }
}
