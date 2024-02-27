package com.eternalcode.example.feature.ignore;

import com.eternalcode.core.feature.ignore.IgnoreService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "api ignore")
public class ApiIgnoreCommand {

    private final IgnoreService ignoreService;

    public ApiIgnoreCommand(IgnoreService ignoreService) {
        this.ignoreService = ignoreService;
    }

    @Execute(name = "ignore")
    void executeIgnore(@Context Player player, @Arg Player target) {
        this.ignoreService.ignore(player.getUniqueId(), target.getUniqueId());
        String message = "You have ignored %s via eternalcore api bridge!";
        player.sendMessage(String.format(message, target.getName()));
    }

    @Execute(name = "unignore")
    void executeUnignore(@Context Player player, @Arg Player target) {
        this.ignoreService.unIgnore(player.getUniqueId(), target.getUniqueId());
        String message = "You have unignored %s via eternalcore api bridge!";
        player.sendMessage(String.format(message, target.getName()));
    }

    @Execute(name = "ignoreall")
    void executeIgnoreAll(@Context Player player) {
        this.ignoreService.ignoreAll(player.getUniqueId());
        player.sendMessage("You have ignored all players via eternalcore api bridge!");
    }

    @Execute(name = "unignoreall")
    void executeUnignoreAll(@Context Player player) {
        this.ignoreService.unIgnoreAll(player.getUniqueId());
        player.sendMessage("You have unignored all players via eternalcore api bridge!");
    }

    @Execute(name = "purge")
    void executePurge(@Context Player player) {
        this.ignoreService.purgeAll();
        player.sendMessage("You have purged all ignored players via eternalcore api bridge!");
    }
}
