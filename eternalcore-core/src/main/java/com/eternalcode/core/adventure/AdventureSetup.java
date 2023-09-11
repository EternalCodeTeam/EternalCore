package com.eternalcode.core.adventure;

import com.eternalcode.core.adventure.legacy.LegacyColorProcessor;
import com.eternalcode.core.adventure.resolver.CenterTagResolver;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;

@BeanSetup
class AdventureSetup {

    @Bean
    AudienceProvider skullAPI(Plugin plugin) {
        return BukkitAudiences.create(plugin);
    }

    @Bean
    MiniMessage miniMessage() {
        return MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .editTags(builder -> builder.resolver(CenterTagResolver.RESOLVER))
            .build();
    }

}
