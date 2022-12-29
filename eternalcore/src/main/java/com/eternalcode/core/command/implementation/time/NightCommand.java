package com.eternalcode.core.command.implementation.time;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "night")
@Permission("eternalcore.night")
public class NightCommand {

    private final NoticeService noticeService;

    public NightCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void day(Player player) {
        player.getWorld().setTime(13700);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(messages -> messages.timeAndWeather().timeSetNight())
            .send();
    }
}
