package com.eternalcode.example.feature.randomteleport;

import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import com.eternalcode.core.feature.randomteleport.RandomTeleportType;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Command(name = "api random-teleport")
public class ApiRandomTeleportCommand {

    private final RandomTeleportService randomTeleportService;

    public ApiRandomTeleportCommand(RandomTeleportService randomTeleportService) {
        this.randomTeleportService = randomTeleportService;
    }

    @Execute(name = "get-safe-random-location-with-attempts")
    void execute(@Context Player player, @Arg int attempts) {
        World world = player.getWorld();

        this.randomTeleportService.getSafeRandomLocation(world, attempts);
    }

    @Execute(name = "get-safe-random-location-with-type-radius-attempts")
    void execute(@Context Player player, @Arg RandomTeleportType type, @Arg int radius, @Arg int attempts) {
        World world = player.getWorld();

        this.randomTeleportService.getSafeRandomLocation(world, type, radius, attempts);
    }

    @Execute(name = "teleport-specific-player")
    void execute(@Context Player player, @Arg Player target) {
        this.randomTeleportService.teleport(target);
    }

    @Execute(name = "teleport-specific-player-world")
    void execute(@Context Player player, @Arg Player target, @Arg World world) {
        this.randomTeleportService.teleport(target, world);
    }

}
