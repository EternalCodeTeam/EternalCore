package com.eternalcode.core.notice;

import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.multification.bukkit.notice.resolver.sound.SoundBukkitResolver;
import com.eternalcode.multification.notice.resolver.NoticeResolverDefaults;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;

@Setup
public class EternalCoreNoticeBukkitResolver {

    @Bean
    NoticeResolverRegistry resolverRegistry() {
        return NoticeResolverDefaults.createRegistry()
            .registerResolver(new SoundBukkitResolver());
    }
}
