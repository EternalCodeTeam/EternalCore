package com.eternalcode.core.feature.weather;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.World;

@Command(name = "sun")
@Permission("eternalcore.sun")
class SunCommand {

    private final NoticeService noticeService;
    private final MinecraftScheduler scheduler;

    @Inject
    SunCommand(NoticeService noticeService, MinecraftScheduler scheduler) {
        this.noticeService = noticeService;
        this.scheduler = scheduler;
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to sun in current world")
    void sun(@Sender Viewer viewer, @Context World world) {
        this.setSun(viewer, world);
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to sun in specified world", arguments = "<world>")
    void sunWorld(@Sender Viewer viewer, @Arg World world) {
        this.setSun(viewer, world);
    }

    private void setSun(Viewer viewer, World world) {
        this.scheduler.run(() -> {
            world.setClearWeatherDuration(20 * 60 * 10);
            world.setStorm(false);
            world.setThundering(false);
        });

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().weatherSetSun())
            .send();
    }

}
