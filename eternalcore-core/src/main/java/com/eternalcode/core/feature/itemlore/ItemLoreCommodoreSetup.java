package com.eternalcode.core.feature.itemlore;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucko.commodore.Commodore;
import org.bukkit.plugin.java.JavaPlugin;

import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

@Controller
public class ItemLoreCommodoreSetup implements Subscriber {

    private final Commodore commodore;

    @Inject
    public ItemLoreCommodoreSetup(Commodore commodore) {
        this.commodore = commodore;
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize(JavaPlugin plugin) {
        LiteralCommandNode<?> itemlore = LiteralArgumentBuilder.literal("itemlore")
            .then(argument("line", IntegerArgumentType.integer())
                .then(argument("text", StringArgumentType.greedyString())))
            .build();

        this.commodore.register(itemlore);
    }


}
