package com.eternalcode.core.scheduler;

import com.eternalcode.commons.bukkit.scheduler.BukkitSchedulerImpl;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscriber;
import org.bukkit.plugin.Plugin;

@BeanSetup
public class SchedulerSetup implements Subscriber {

    @Bean
    public Scheduler scheduler(Plugin plugin) {
        return new BukkitSchedulerImpl(plugin);
    }
}
