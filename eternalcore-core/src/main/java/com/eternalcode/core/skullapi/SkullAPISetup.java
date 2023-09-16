package com.eternalcode.core.skullapi;

import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscribe;
import dev.rollczi.liteskullapi.LiteSkullFactory;
import dev.rollczi.liteskullapi.SkullAPI;
import org.bukkit.plugin.Plugin;

@BeanSetup
class SkullAPISetup implements Subscriber {

    @Bean
    public SkullAPI skullAPI(Plugin plugin) {
        return LiteSkullFactory.builder()
            .bukkitScheduler(plugin)
            .build();
    }

    @Subscribe(EternalShutdownEvent.class)
    public void onShutdown(SkullAPI skullAPI) {
        skullAPI.shutdown();
    }

}
