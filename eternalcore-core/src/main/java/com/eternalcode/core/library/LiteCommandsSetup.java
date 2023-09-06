package com.eternalcode.core.library;

import com.eternalcode.core.event.EternalCoreInitializeEvent;
import com.eternalcode.core.event.EternalCoreShutdownEvent;
import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.injector.annotations.component.Controller;
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
@Controller
class LiteCommandsSetup implements Subscriber {

    @Bean
    public LiteCommandsBuilder<CommandSender> liteCommandsBuilder(Server server, AudienceProvider audiencesProvider, MiniMessage miniMessage) {
        return LiteBukkitAdventurePlatformFactory.builder(server, "eternalcore", false, audiencesProvider, miniMessage);
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
