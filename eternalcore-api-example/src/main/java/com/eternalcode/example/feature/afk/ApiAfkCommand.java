package com.eternalcode.example.feature.afk;

import com.eternalcode.core.feature.afk.Afk;
import com.eternalcode.core.feature.afk.AfkReason;
import com.eternalcode.core.feature.afk.AfkService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Command(name = "api afk")
public class ApiAfkCommand {

    private static final DateTimeFormatter AFK_TIME_FORMATTER = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault());

    private final AfkService afkService;

    public ApiAfkCommand(AfkService afkService) {
        this.afkService = afkService;
    }

    @Execute(name = "isafk")
    void execute(@Context Player player, @Arg Player target) {
        boolean afk = this.afkService.isAfk(target.getUniqueId());
        String message = "Player %s is %s afk, used via eternalcore api bridge!";
        player.sendMessage(String.format(message, target.getName(), afk ? "now" : "not"));
    }

    @Execute(name = "setafk")
    void executeSetAfk(@Context Player player, @Arg Player target) {
        Afk afk = this.afkService.markAfk(target.getUniqueId(), AfkReason.COMMAND);

        AfkReason afkReason = afk.getAfkReason();
        Instant start = afk.getStart();
        UUID targetPlayer = afk.getPlayer();

        String message = "You have set %s as afk via eternalcore api bridge!";
        player.sendMessage(String.format(message, target.getName()));

        String formattedDateTime = AFK_TIME_FORMATTER.format(start);

        player.sendMessage("The reason for being afk is: " + afkReason);
        player.sendMessage("The start time of being afk is: " + formattedDateTime);
        player.sendMessage("The player's UUID is: " + targetPlayer);
    }

    @Execute(name = "removeafk")
    void executeRemoveAfk(@Context Player player, @Arg Player target) {
        this.afkService.clearAfk(target.getUniqueId());
        String message = "You have removed %s from afk via eternalcore api bridge!";
        player.sendMessage(String.format(message, target.getName()));
    }

    @Execute(name = "addinteraction")
    void executeMarkInteraction(@Context Player player, @Arg Player target, @Arg int interactions) {
        for (int i = 0; i < interactions; i++) {
            this.afkService.markInteraction(target.getUniqueId());
        }

        String message = "You added %d interactions to %s via eternalcore api bridge!";
        player.sendMessage(String.format(message, interactions, target.getName()));
        player.sendMessage("Default configuration of the plugin requires 20 interactions to mark the player as active.");
    }

    @Execute(name = "demo")
    void executeDemo(@Context Player player) {
        player.showDemoScreen();
    }

}
