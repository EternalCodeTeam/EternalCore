package com.eternalcode.example.feature.ignore;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.ignore.IgnoreService;
import com.eternalcode.core.feature.ignore.event.IgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "api ignore")
public class ApiIgnoreCommand {

    private final IgnoreService ignoreService;
    private final EventCaller caller;

    public ApiIgnoreCommand(IgnoreService ignoreService, EventCaller caller) {
        this.ignoreService = ignoreService;
        this.caller = caller;
    }

    @Execute(name = "ignore")
    void executeIgnore(@Context Player player, @Arg Player target) {
        IgnoreEvent event = this.caller.callEvent(new IgnoreEvent(player, target));

        if (event.isCancelled()) {
            return;
        }

        this.ignoreService.ignore(player.getUniqueId(), target.getUniqueId());
        String message = "You have ignored %s via eternalcore api bridge!";
        player.sendMessage(String.format(message, target.getName()));
    }

    @Execute(name = "unignore")
    void executeUnignore(@Context Player player, @Arg Player target) {
        UnIgnoreEvent event = this.caller.callEvent(new UnIgnoreEvent(player, target));

        if (event.isCancelled()) {
            return;
        }

        this.ignoreService.unIgnore(player.getUniqueId(), target.getUniqueId());
        String message = "You have unignored %s via eternalcore api bridge!";
        player.sendMessage(String.format(message, target.getName()));
    }

    @Execute(name = "ignoreall")
    void executeIgnoreAll(@Context Player player) {
        IgnoreAllEvent event = this.caller.callEvent(new IgnoreAllEvent(player));

        if (event.isCancelled()) {
            return;
        }

        this.ignoreService.ignoreAll(player.getUniqueId());
        player.sendMessage("You have ignored all players via eternalcore api bridge!");
    }

    @Execute(name = "unignoreall")
    void executeUnignoreAll(@Context Player player) {
        UnIgnoreAllEvent event = this.caller.callEvent(new UnIgnoreAllEvent(player));

        if (event.isCancelled()) {
            return;
        }
        this.ignoreService.unIgnoreAll(player.getUniqueId());
        player.sendMessage("You have unignored all players via eternalcore api bridge!");
    }
}
