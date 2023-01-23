package com.eternalcode.core.feature.essentials.weather;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;

@Route(name = "thunder", aliases = "storm")
@Permission("eternalcore.thunder")
public class ThunderCommand {

    private final NoticeService noticeService;

    public ThunderCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void thunder(Viewer viewer, @Arg World world) {
        world.setStorm(true);
        world.setThundering(true);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().weatherSetThunder())
            .send();
    }

}
