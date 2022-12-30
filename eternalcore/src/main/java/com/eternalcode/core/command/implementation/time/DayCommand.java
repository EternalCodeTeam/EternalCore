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
    void day(Player player, Viewer viewer) {
        this.day(viewer, player.getWorld());
    }

    @Execute(required = 1)
    void day(Viewer viewer, @Arg World world) {
        world.setTime(100);

        this.noticeService.viewer(viewer, messages -> messages.timeAndWeather().timeSetDay());
    }

}
