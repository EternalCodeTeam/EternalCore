package com.eternalcode.core.command.implementation.weather;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Route(name = "sun")
@Permission("eternalcore.sun")
public class SunCommand {

    private final NoticeService noticeService;

    public SunCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void sun(Player player, Viewer viewer) {
        this.sun(viewer, player.getWorld());
    }

    @Execute(required = 1)
    void sun(Viewer viewer, @Arg World world) {
        world.setClearWeatherDuration(20 * 60 * 10);
        world.setStorm(false);
        world.setThundering(false);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(messages -> messages.timeAndWeather().weatherSetSun())
            .send();
    }

}
