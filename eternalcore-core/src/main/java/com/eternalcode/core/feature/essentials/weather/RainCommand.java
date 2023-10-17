package com.eternalcode.core.feature.essentials.weather;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.World;

@Command(name = "rain")
@Permission("eternalcore.rain")
class RainCommand {

    private final NoticeService noticeService;

    @Inject
    RainCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to rain in current world")
    void rain(@Context Viewer viewer, @Context World world) {
        this.setRain(viewer, world);
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to rain in specified world", arguments = "<world>")
    void rainWorld(@Context Viewer viewer, @Arg World world) {
        this.setRain(viewer, world);
    }

    private void setRain(Viewer viewer, World world) {
        world.setStorm(true);
        world.setThundering(false);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().weatherSetRain())
            .send();
    }

}
