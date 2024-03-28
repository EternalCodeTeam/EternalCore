package com.eternalcode.example.feature.home;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "api home")
public class ApiHomeCommand {

    private final HomeService homeService;

    public ApiHomeCommand(HomeService homeService) {
        this.homeService = homeService;
    }


    @Execute(name = "set")
    void execute(@Context Player player, @Arg String name) {
        this.homeService.hasHomeWithSpecificName(player, name).thenAccept(hasHome -> {
            if (hasHome) {
                player.sendMessage("You already have a home with this name!");
                return;
            }

            this.homeService.createHome(name, player.getUniqueId(), player.getLocation());
        });
    }

    @Execute(name = "delete")
    void delete(@Context Player player, @Arg String name) {
        this.homeService.getHome(player, name).thenAccept(optionalHome -> {
            if (optionalHome.isEmpty()) {
                player.sendMessage("You don't have a home with this name!");
                return;
            }

            this.homeService.deleteHome(player, name);
        });
    }

    @Execute(name = "list")
    void list(@Context Player player) {
        this.homeService.getHomes(player).thenAccept(homes -> {
            if (homes.isEmpty()) {
                player.sendMessage("You don't have any homes!");
                return;
            }

            player.sendMessage("Your homes:");
            homes.forEach(home -> player.sendMessage(home.getName()));
        });
    }

}
