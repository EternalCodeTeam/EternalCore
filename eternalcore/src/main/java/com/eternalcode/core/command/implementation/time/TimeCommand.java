package com.eternalcode.core.command.implementation.time;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Route(name = "time")
@Permission("eternalcore.time")
public class TimeCommand {

    private final NoticeService noticeService;

    public TimeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(route = "add")
    void add(Player player, @Arg Integer time) {
        World world = player.getWorld();

        world.setTime(world.getTime() + time);

        this.noticeService.create()
            .player(player.getUniqueId())
            .placeholder("{TIME}", String.valueOf(time))
            .notice(messages -> messages.timeAndWeather().timeAdd())
            .send();
    }

    @Execute(route = "set")
    void set(Player player, @Arg Integer time) {
        player.getWorld().setTime(time);

        this.noticeService.create()
            .player(player.getUniqueId())
            .placeholder("{TIME}", String.valueOf(time))
            .notice(messages -> messages.timeAndWeather().timeSet())
            .send();
    }

}
