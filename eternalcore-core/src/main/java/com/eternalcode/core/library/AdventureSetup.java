package com.eternalcode.core.library;

import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.util.legacy.LegacyColorProcessor;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;

@BeanSetup
public class AdventureSetup {

    @Bean
    AudienceProvider skullAPI(Plugin plugin) {
        return BukkitAudiences.create(plugin);
    }

    @Bean
    MiniMessage miniMessage() {
        return MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .build();
    }

}
