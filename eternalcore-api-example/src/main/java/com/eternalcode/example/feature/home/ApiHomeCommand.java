package com.eternalcode.example.feature.home;

import com.eternalcode.core.feature.home.HomeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "api home")
public class ApiHomeCommand {

    private final HomeService homeService;

    public ApiHomeCommand(HomeService homeService) {
        this.homeService = homeService;
    }

    @Execute(name = "set")
    void executeSet(@Sender Player player, @Arg("homeName") String homeName) {
        this.homeService.createHome(player.getUniqueId(), homeName, player.getLocation());
        player.sendMessage("Home set!");
    }

    @Execute(name = "teleport")
    void executeTeleport(@Sender Player player, @Arg("homeName") String homeName) {
        Location location = this.homeService.getHome(player.getUniqueId(), homeName)
            .map(home -> home.getLocation())
            .orElse(null);

        if (location == null) {
            player.sendMessage("Home not found!");
            return;
        }

        player.teleport(location);
        player.sendMessage("Teleported to home!");
    }

    @Execute(name = "delete")
    void executeDelete(@Sender Player player, @Arg("homeName") String homeName) {
        this.homeService.deleteHome(player.getUniqueId(), homeName);
        player.sendMessage("Home deleted!");
    }

    @Execute(name = "list")
    void executeList(@Sender Player player) {
        player.sendMessage("Your homes:");
        this.homeService.getHomes(player.getUniqueId())
            .forEach(home -> player.sendMessage(home.getName()));
    }
}
