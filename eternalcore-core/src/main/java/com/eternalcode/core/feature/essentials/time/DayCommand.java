package com.eternalcode.core.feature.essentials.time;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;

@Route(name = "day")
@Permission("eternalcore.day")
public class DayCommand {

    private final NoticeService noticeService;

    public DayCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Sets time to day in specified world", arguments = "<world>")
    void day(Viewer viewer, @Arg World world) {
        world.setTime(100);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().timeSetDay())
            .send();
    }

}
