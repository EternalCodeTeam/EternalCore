package com.eternalcode.example.feature.randomteleport;

import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Command(name = "api random-teleport")
public class ApiRandomTeleportCommand {

    private final RandomTeleportService randomTeleportService;

    public ApiRandomTeleportCommand(RandomTeleportService randomTeleportService) {
        this.randomTeleportService = randomTeleportService;
    }

    @Execute(name = "safe-random-location")
    void execute(@Context Player player, @Arg int attempts) {
        World world = player.getWorld();

        CompletableFuture<Location> safeRandomLocation =
            this.randomTeleportService.getSafeRandomLocation(world, attempts);

        safeRandomLocation.thenAccept(location -> player.sendMessage("Safe random location: " + location));
    }

    @Execute(name = "safe-random-location-with-radius")
    void execute(@Context Player player, @Arg int radius, @Arg int attempts) {
        World world = player.getWorld();

        CompletableFuture<Location> safeRandomLocation =
            this.randomTeleportService.getSafeRandomLocation(world, radius, attempts);

        safeRandomLocation.thenAccept(location -> player.sendMessage("Safe random location: " + location));
    }

    @Execute(name = "teleport")
    void execute(@Context Player player, @Arg Player target) {
        this.randomTeleportService.teleport(target);
    }

    @Execute(name = "teleport")
    void execute(@Context Player player, @Arg Player target, @Arg World world) {
        this.randomTeleportService.teleport(target, world);
    }
}
