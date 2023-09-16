package com.eternalcode.core.litecommand;

import com.eternalcode.core.litecommand.configurator.config.CommandConfiguration;
import com.eternalcode.core.injector.bean.BeanFactory;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscribe;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.bukkit.adventure.platform.LiteBukkitAdventurePlatformFactory;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@BeanSetup
class LiteCommandsSetup implements Subscriber {

    @Bean
    public LiteCommandsBuilder<CommandSender> liteCommandsBuilder(Server server, AudienceProvider audiencesProvider, MiniMessage miniMessage, CommandConfiguration commandConfiguration) {
        return LiteBukkitAdventurePlatformFactory.builder(server, "eternalcore", false, audiencesProvider, miniMessage);
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onEnable(BeanFactory beanFactory, LiteCommandsBuilder<CommandSender> liteCommandsBuilder) {
        LiteCommands<CommandSender> register = liteCommandsBuilder.register();
        beanFactory.addCandidate(LiteCommands.class, () -> register);
    }

    @Subscribe(EternalShutdownEvent.class)
    public void onShutdown(LiteCommands<CommandSender> liteCommands) {
        liteCommands.getPlatform().unregisterAll();
    }

}
