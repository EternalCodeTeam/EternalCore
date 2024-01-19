package com.eternalcode.core.feature.essentials.time;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Command(name = "time")
@Permission("eternalcore.time")
class TimeCommand {

    private final NoticeService noticeService;

    @Inject
    TimeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Adds specified amount of time to specified world", arguments = "<time>")
    void add(@Context Player player, @Context Viewer viewer, @Arg(TimeArgument.KEY) int time) {
        this.add(viewer, time, player.getWorld());
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Add specified amount of time to specified world", arguments = "<time> <world>")
    void add(@Context Viewer viewer, @Arg(TimeArgument.KEY) int time, @Arg World world) {
        world.setTime(world.getTime() + time);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{TIME}", String.valueOf(time))
            .notice(translation -> translation.timeAndWeather().timeAdd())
            .send();
    }

    @Execute(name = "set")
    @DescriptionDocs(description = "Sets specified time", arguments = "<time>")
    void set(@Context Player player, @Context Viewer viewer, @Arg(TimeArgument.KEY) int time) {
        this.set(viewer, time, player.getWorld());
    }

    @Execute(name = "set")
    @DescriptionDocs(description = "Sets specified time to specified world", arguments = "<time> <world>")
    void set(@Context Viewer viewer, @Arg(TimeArgument.KEY) int time, @Arg World world) {
        world.setTime(time);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{TIME}", String.valueOf(time))
            .notice(translation -> translation.timeAndWeather().timeSet())
            .send();
    }

}
