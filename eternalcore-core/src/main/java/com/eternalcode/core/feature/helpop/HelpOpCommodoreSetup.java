package com.eternalcode.core.feature.helpop;

import com.eternalcode.core.bridge.litecommand.configurator.config.Command;
import com.eternalcode.core.bridge.litecommand.configurator.config.CommandConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.lucko.commodore.Commodore;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

@Controller
public class HelpOpCommodoreSetup implements Subscriber {

    private final Commodore commodore;
    private final CommandConfiguration commandConfiguration;

    @Inject
    public HelpOpCommodoreSetup(Commodore commodore, CommandConfiguration commandConfiguration) {
        this.commodore = commodore;
        this.commandConfiguration = commandConfiguration;
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize(JavaPlugin plugin) {
        Command command = this.commandConfiguration.commands.get("helpop");

        if (command == null) {
            return;
        }

        String name = command.name;
        List<String> aliases = command.aliases;

        if (aliases == null || name == null) {
            return;
        }

        LiteralArgumentBuilder<?> helpop = setHelpopSuggestions(name);

        this.commodore.register(helpop);

        for (String alias : aliases) {
            LiteralArgumentBuilder<?> helpopAlias = setHelpopSuggestions(alias);

            this.commodore.register(helpopAlias);
        }

    }

    private static LiteralArgumentBuilder<Object> setHelpopSuggestions(String alias) {
        return literal(alias).then(argument("message", StringArgumentType.greedyString()));
    }
}