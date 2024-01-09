package com.eternalcode.core.feature.itemlore;

import com.eternalcode.core.bridge.commodore.CommodoreService;
import com.eternalcode.core.bridge.litecommand.configurator.config.Command;
import com.eternalcode.core.bridge.litecommand.configurator.config.CommandConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.rollczi.litecommands.command.CommandManager;
import dev.rollczi.litecommands.command.CommandRoute;
import me.lucko.commodore.Commodore;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

@Controller
public class ItemLoreCommodoreSetup implements Subscriber {

    public static final String ITEM_LORE_COMMAND = "itemlore";
    private final Commodore commodore;
    private final CommodoreService commodoreService;

    @Inject
    public ItemLoreCommodoreSetup(Commodore commodore, CommodoreService commodoreService) {
        this.commodore = commodore;
        this.commodoreService = commodoreService;
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize(JavaPlugin plugin) {
        String commandName = this.commodoreService.findCommandName(ITEM_LORE_COMMAND);
        List<String> aliases = this.commodoreService.findCommandAliases(ITEM_LORE_COMMAND);

        if (commandName == null) {
            return;
        }

        LiteralArgumentBuilder<?> itemlore = setItemLoreSuggestions(commandName);
        this.commodore.register(itemlore);

        if (aliases == null) {
            return;
        }

        for (String alias : aliases) {
            LiteralArgumentBuilder<?> itemLoreAlias = setItemLoreSuggestions(alias);
            this.commodore.register(itemLoreAlias);
        }
    }

    private static LiteralArgumentBuilder<Object> setItemLoreSuggestions(String alias) {
        return literal(alias).then(literal("set")
                .then(argument("line", IntegerArgumentType.integer())
                    .then(argument("text", StringArgumentType.greedyString()))))
            .then(literal("clear"));
    }

}
