package com.eternalcode.core.command.implementation.time;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
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

    private final NoticeService noticeService;

    public TimeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(route = "add", required = 1)
    void add(Player player, Viewer viewer, @Arg @Name("time") int time) {
        this.add(viewer, time, player.getWorld());
    }

    @Execute(route = "add", required = 2)
    void add(Viewer viewer, @Arg @Name("time") int time, @Arg World world) {
        world.setTime(world.getTime() + time);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{TIME}", String.valueOf(time))
            .notice(translation -> translation.timeAndWeather().timeAdd())
            .send();
    }

    @Execute(route = "set", required = 1)
    void set(Player player, Viewer viewer, @Arg @Name("time") int time) {
        this.set(viewer, time, player.getWorld());
    }

    @Execute(route = "set", required = 2)
    void set(Viewer viewer, @Arg @Name("time") int time, @Arg World world) {
        world.setTime(time);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{TIME}", String.valueOf(time))
            .notice(translation -> translation.timeAndWeather().timeSet())
            .send();
    }

}
