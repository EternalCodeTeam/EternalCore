package com.eternalcode.core.litecommand;

import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.injector.bean.BeanFactory;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.adventure.bukkit.platform.LiteAdventurePlatformExtension;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.folia.FoliaExtension;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@Setup
class LiteCommandsSetup {

    @Bean
    public LiteCommandsBuilder<CommandSender, ?, ?> liteCommandsBuilder(
        Plugin plugin,
        Server server,
        AudienceProvider audiencesProvider,
        MiniMessage miniMessage,
        NoticeService noticeService,
        LiteCommandsAnnotations<CommandSender> liteCommandsAnnotations
    ) {
        return LiteBukkitFactory.builder("eternalcore", plugin, server)
            .extension(new FoliaExtension(plugin))
            .commands(liteCommandsAnnotations)
            .message(LiteBukkitMessages.WORLD_NOT_EXIST, (invocation, world) -> noticeService.create()
                .sender(invocation.sender())
                .notice(translation -> translation.argument().worldDoesntExist())
                .placeholder("{WORLD}", world)
            )
            .message(LiteBukkitMessages.LOCATION_INVALID_FORMAT, (invocation, input) -> noticeService.create()
                .sender(invocation.sender())
                .notice(translation -> translation.argument().incorrectLocation())
                .placeholder("{LOCATION}", input)
            )
            .extension(new LiteAdventurePlatformExtension<CommandSender>(audiencesProvider), extension -> extension
                .serializer(miniMessage)
            );
    }

    @Bean
    public LiteCommandsAnnotations<CommandSender> liteCommandsAnnotations() {
        return LiteCommandsAnnotations.create();
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onEnable(BeanFactory beanFactory, LiteCommandsBuilder<CommandSender, ?, ?> liteCommandsBuilder) {
        LiteCommands<CommandSender> register = liteCommandsBuilder.build();
        beanFactory.addCandidate(LiteCommands.class, () -> register);
        beanFactory.initializeCandidates(LiteCommands.class);
    }

    @Subscribe(EternalShutdownEvent.class)
    public void onShutdown(LiteCommands<CommandSender> liteCommands) {
        liteCommands.unregister();
    }

}
