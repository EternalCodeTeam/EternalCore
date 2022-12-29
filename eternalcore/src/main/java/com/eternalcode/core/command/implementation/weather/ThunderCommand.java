package com.eternalcode.core.command.implementation.weather;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "thunder", aliases = "storm")
@Permission("eternalcore.thunder")
public class ThunderCommand {

    private final NoticeService noticeService;

    public ThunderCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void sun(Player player) {
        player.getWorld().setStorm(true);
        player.getWorld().setThundering(true);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(messages -> messages.timeAndWeather().weatherSetThunder())
            .send();
    }

}
