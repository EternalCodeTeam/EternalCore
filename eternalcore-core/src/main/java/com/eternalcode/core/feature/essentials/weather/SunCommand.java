package com.eternalcode.core.feature.essentials.weather;

import com.eternalcode.annotations.scan.command.DocsDescription;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;

@Route(name = "sun")
@Permission("eternalcore.sun")
public class SunCommand {

    private final NoticeService noticeService;

    public SunCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DocsDescription(description = "Sets weather to sun in specified world", arguments = "<world>")
    void sun(Viewer viewer, @Arg World world) {
        world.setClearWeatherDuration(20 * 60 * 10);
        world.setStorm(false);
        world.setThundering(false);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().weatherSetSun())
            .send();
    }

}
