package com.eternalcode.core.feature.time;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.World;

@Command(name = "day")
@Permission("eternalcore.day")
class DayCommand {

    private final NoticeService noticeService;

    @Inject
    DayCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Sets time to day in current world")
    void day(@Context Viewer viewer, @Context World world) {
        this.setDay(viewer, world);
    }

    @Execute
    @DescriptionDocs(description = "Sets time to day in specified world", arguments = "<world>")
    void dayWorld(@Context Viewer viewer, @Arg World world) {
        this.setDay(viewer, world);
    }

    private void setDay(Viewer viewer, World world) {
        world.setTime(100);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().timeSetDay())
            .send();
    }

}
