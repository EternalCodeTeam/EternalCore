package com.eternalcode.core.feature.essentials.weather;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;

@Route(name = "rain")
@Permission("eternalcore.rain")
class RainCommand {

    private final NoticeService noticeService;

    @Inject
    RainCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to rain in specified world", arguments = "<world>")
    void rain(Viewer viewer, @Arg World world) {
        world.setStorm(true);
        world.setThundering(false);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().weatherSetRain())
            .send();
    }

}
