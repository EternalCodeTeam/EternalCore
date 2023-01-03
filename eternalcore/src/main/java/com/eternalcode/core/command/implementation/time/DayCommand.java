package com.eternalcode.core.command.implementation.time;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Route(name = "day")
@Permission("eternalcore.day")
public class DayCommand {

    private final NoticeService noticeService;

    public DayCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void day(Viewer viewer, @Arg World world) {
        world.setTime(100);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(messages -> messages.timeAndWeather().timeSetDay())
            .send();
    }

}
