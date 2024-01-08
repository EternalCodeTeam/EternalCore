package com.eternalcode.core.feature.reportchat;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.lucko.commodore.Commodore;
import org.bukkit.plugin.java.JavaPlugin;

import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

@Controller
public class HelpOpCommodoreSetup implements Subscriber {

    private final Commodore commodore;

    @Inject
    public HelpOpCommodoreSetup(Commodore commodore) {
        this.commodore = commodore;
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize(JavaPlugin plugin) {
        LiteralArgumentBuilder<Object> then = LiteralArgumentBuilder.literal("helpop")
            .then(argument("message", StringArgumentType.greedyString()));

        this.commodore.register(then);
    }


}
