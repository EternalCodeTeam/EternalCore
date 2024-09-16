package com.eternalcode.core.feature.essentials.weather;

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

@Command(name = "thunder", aliases = "storm")
@Permission("eternalcore.thunder")
class ThunderCommand {

    private final NoticeService noticeService;
    private final Scheduler scheduler;

    @Inject
    ThunderCommand(NoticeService noticeService, Scheduler scheduler) {
        this.noticeService = noticeService;
        this.scheduler = scheduler;
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to thunder in current world")
    void thunder(@Context Viewer viewer, @Context World world) {
        this.setThunder(viewer, world);
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to thunder in specified world", arguments = "<world>")
    void thunderWorld(@Context Viewer viewer, @Arg World world) {
        this.setThunder(viewer, world);
    }

    private void setThunder(Viewer viewer, World world) {
        this.scheduler.sync(() -> {
            world.setStorm(true);
            world.setThundering(true);
        });

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().weatherSetThunder())
            .send();
    }

}
