package com.eternalcode.core.command.implementation.time;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Route(name = "time")
@Permission("eternalcore.time")
public class TimeCommand {

    @Execute(route = "add")
    void add(Player player, @Arg @Name("number") Integer time) {
        World world = player.getWorld();

        world.setTime(world.getTime() + time);
    }

    @Execute(route = "set")
    void set(Player player, @Arg @Name("number") Integer time) {
        player.getWorld().setTime(time);
    }

}
