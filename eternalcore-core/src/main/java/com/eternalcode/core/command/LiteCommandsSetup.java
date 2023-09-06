package com.eternalcode.core.command;

import com.eternalcode.core.command.configurator.CommandConfigurator;
import com.eternalcode.core.command.configurator.GameModeConfigurator;
import com.eternalcode.core.command.configurator.config.CommandConfiguration;
import com.eternalcode.core.event.EternalCoreInitializeEvent;
import com.eternalcode.core.event.EternalCoreShutdownEvent;
import com.eternalcode.core.feature.essentials.gamemode.GameModeCommand;
import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.bukkit.adventure.platform.LiteBukkitAdventurePlatformFactory;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@BeanSetup
@Subscriber
class LiteCommandsSetup {

    @Bean
    public LiteCommandsBuilder<CommandSender> liteCommandsBuilder(Server server, AudienceProvider audiencesProvider, MiniMessage miniMessage, CommandConfiguration commandConfiguration) {
        return LiteBukkitAdventurePlatformFactory.builder(server, "eternalcore", false, audiencesProvider, miniMessage)
            .commandGlobalEditor(new CommandConfigurator(commandConfiguration))
            .commandEditor(GameModeCommand.class, new GameModeConfigurator(commandConfiguration));
    }

    @Subscribe(EternalCoreInitializeEvent.class)
    public void onEnable(DependencyInjector dependencyInjector, LiteCommandsBuilder<CommandSender> liteCommandsBuilder) {
        LiteCommands<CommandSender> register = liteCommandsBuilder.register();
        dependencyInjector.register(LiteCommands.class, () -> register);
    }

    @Subscribe(EternalCoreShutdownEvent.class)
    public void onShutdown(LiteCommands<CommandSender> liteCommands) {
        liteCommands.getPlatform().unregisterAll();
    }

}
