package com.eternalcode.core.command.implementation.time;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "day")
@Permission("eternalcore.day")
public class DayCommand {

    private final NoticeService noticeService;

    public DayCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void day(Player player) {
        player.getWorld().setTime(100);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(messages -> messages.timeAndWeather().timeSetDay())
            .send();
    }

}
