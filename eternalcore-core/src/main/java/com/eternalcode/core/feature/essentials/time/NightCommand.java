package com.eternalcode.core.feature.essentials.time;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.World;

@Command(name = "night")
@Permission("eternalcore.night")
class NightCommand {

    private final NoticeService noticeService;
    private final Scheduler scheduler;

    @Inject
    NightCommand(NoticeService noticeService, Scheduler scheduler) {
        this.noticeService = noticeService;
        this.scheduler = scheduler;
    }

    @Execute
    @DescriptionDocs(description = "Sets time to night in current world")
    void night(@Context Viewer viewer, @Context World world) {
        this.setNight(viewer, world);
    }

    @Execute
    @DescriptionDocs(description = "Sets time to night in specified world", arguments = "<world>")
    void nightWorld(@Context Viewer viewer, @Arg World world) {
        this.setNight(viewer, world);
    }

    private void setNight(Viewer viewer, World world) {
        this.scheduler.sync(() -> {
            world.setTime(13700);
        });

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().timeSetNight())
            .send();
    }

}
