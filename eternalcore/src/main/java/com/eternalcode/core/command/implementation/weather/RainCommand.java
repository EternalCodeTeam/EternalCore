package com.eternalcode.core.command.implementation.weather;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;

@Route(name = "rain")
@Permission("eternalcore.rain")
public class RainCommand {

    private final NoticeService noticeService;

    public RainCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void rain(Viewer viewer, @Arg World world) {
        world.setStorm(true);
        world.setThundering(false);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(messages -> messages.timeAndWeather().weatherSetRain())
            .send();
    }

}
