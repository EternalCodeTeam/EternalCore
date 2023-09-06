package com.eternalcode.core.skull;

import com.eternalcode.core.event.EternalCoreShutdownEvent;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import dev.rollczi.liteskullapi.LiteSkullFactory;
import dev.rollczi.liteskullapi.SkullAPI;
import org.bukkit.plugin.Plugin;

@BeanSetup
@Subscriber
class SkullAPISetup {

    @Bean
    public SkullAPI skullAPI(Plugin plugin) {
        return LiteSkullFactory.builder()
            .bukkitScheduler(plugin)
            .build();
    }

    @Subscribe(EternalCoreShutdownEvent.class)
    public void onShutdown(SkullAPI skullAPI) {
        skullAPI.shutdown();
    }

}
