package com.eternalcode.core.feature.essentials.time;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
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

    @Inject
    public TimeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(route = "add", required = 1)
    @DescriptionDocs(description = "Adds specified amount of time to specified world", arguments = "<time>")
    void add(Player player, Viewer viewer, @Arg int time) {
        this.add(viewer, time, player.getWorld());
    }

    @Execute(route = "add", required = 2)
    @DescriptionDocs(description = "Add specified amount of time to specified world", arguments = "<time> <world>")
    void add(Viewer viewer, @Arg int time, @Arg World world) {
        world.setTime(world.getTime() + time);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{TIME}", String.valueOf(time))
            .notice(translation -> translation.timeAndWeather().timeAdd())
            .send();
    }

    @Execute(route = "set", required = 1)
    @DescriptionDocs(description = "Sets specified time", arguments = "<time>")
    void set(Player player, Viewer viewer, @Arg int time) {
        this.set(viewer, time, player.getWorld());
    }

    @Execute(route = "set", required = 2)
    @DescriptionDocs(description = "Sets specified time to specified world", arguments = "<time> <world>")
    void set(Viewer viewer, @Arg int time, @Arg World world) {
        world.setTime(time);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{TIME}", String.valueOf(time))
            .notice(translation -> translation.timeAndWeather().timeSet())
            .send();
    }

}
