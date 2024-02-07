package com.eternalcode.example.feature.spawn;

import com.eternalcode.core.feature.spawn.SpawnService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "api spawn")
public class ApiSpawnCommand {

    private final SpawnService spawnService;

    public ApiSpawnCommand(SpawnService spawnService) {
        this.spawnService = spawnService;
    }

    @Execute(name = "set")
    void execute(@Context Player player) {
        this.spawnService.setSpawnLocation(player.getLocation());
        player.sendMessage("You have set the spawn location via EternalCore developer api bridge, congratulations!");
    }

    @Execute(name = "teleport")
    void executeTeleport(@Context Player player, @Arg Player target) {
        this.spawnService.teleportToSpawn(target);
        String message = "You have teleported %s to the spawn location via EternalCore developer api bridge, congratulations!";
        player.sendMessage(String.format(message, target.getName()));
    }

    @Execute(name = "spawnlocation")
    void executeSpawnLocation(@Context Player player) {
        player.sendMessage("The spawn location is set at: " + this.spawnService.getSpawnLocation());
    }
}
