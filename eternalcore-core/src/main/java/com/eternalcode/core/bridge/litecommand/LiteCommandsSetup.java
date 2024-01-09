package com.eternalcode.core.bridge.litecommand;

import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.injector.bean.BeanFactory;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.adventure.bukkit.platform.LiteAdventurePlatformExtension;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import dev.rollczi.litecommands.command.CommandManager;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@BeanSetup
class LiteCommandsSetup implements Subscriber {

    @Bean
    public LiteCommandsBuilder<CommandSender, ?, ?> liteCommandsBuilder(
        Plugin plugin,
        Server server,
        AudienceProvider audiencesProvider,
        MiniMessage miniMessage,
        LiteCommandsAnnotations<CommandSender> liteCommandsAnnotations
    ) {
        return LiteCommandsBukkit.builder("eternalcore", plugin, server)
            .commands(liteCommandsAnnotations)
            .extension(new LiteAdventurePlatformExtension<CommandSender>(audiencesProvider), extension -> extension
                .serializer(miniMessage)
            );
    }

    @Bean
    public LiteCommandsAnnotations<CommandSender> liteCommandsAnnotations() {
        return LiteCommandsAnnotations.create();
    }

    @Bean
    public LiteCommands<CommandSender> liteCommands(LiteCommandsBuilder<CommandSender, ?, ?> liteCommandsBuilder) {
        return liteCommandsBuilder.build();
    }

    @Bean
    public CommandManager<CommandSender> commandManager(LiteCommands<CommandSender> liteCommands) {
        return liteCommands.getCommandManager();
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onEnable(BeanFactory beanFactory, LiteCommands<CommandSender> register) {
        beanFactory.addCandidate(LiteCommands.class, () -> register);
    }

    @Subscribe(EternalShutdownEvent.class)
    public void onShutdown(LiteCommands<CommandSender> liteCommands) {
        liteCommands.unregister();
    }

}
