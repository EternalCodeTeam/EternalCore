package com.eternalcode.core.command.implementation.weather;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "rain")
@Permission("eternalcore.rain")
public class RainCommand {

    private final NoticeService noticeService;

    public RainCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void sun(Player player) {
        player.getWorld().setStorm(true);
        player.getWorld().setThundering(false);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(messages -> messages.timeAndWeather().weatherSetRain())
            .send();
    }

}
