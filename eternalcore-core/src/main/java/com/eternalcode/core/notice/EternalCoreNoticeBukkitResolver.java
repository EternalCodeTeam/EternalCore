package com.eternalcode.core.notice;

import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.multification.bukkit.notice.resolver.sound.SoundBukkitResolver;
import com.eternalcode.multification.notice.resolver.NoticeResolverDefaults;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.notice.resolver.actionbar.ActionbarResolver;
import com.eternalcode.multification.notice.resolver.chat.ChatResolver;
import com.eternalcode.multification.notice.resolver.sound.SoundAdventureResolver;
import com.eternalcode.multification.notice.resolver.title.SubtitleResolver;
import com.eternalcode.multification.notice.resolver.title.SubtitleWithEmptyTitleResolver;
import com.eternalcode.multification.notice.resolver.title.TimesResolver;
import com.eternalcode.multification.notice.resolver.title.TitleHideResolver;
import com.eternalcode.multification.notice.resolver.title.TitleResolver;
import com.eternalcode.multification.notice.resolver.title.TitleWithEmptySubtitleResolver;

@BeanSetup
public class EternalCoreNoticeBukkitResolver {

    @Bean
    public NoticeResolverRegistry resolverRegistry() {
        return NoticeResolverDefaults.createRegistry()
            .registerResolver(new SoundBukkitResolver());
    }
}
