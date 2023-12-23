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
import dev.rollczi.litecommands.message.LiteMessages;
import dev.rollczi.litecommands.time.DurationParser;
import dev.rollczi.litecommands.time.TemporalAmountParser;
import org.bukkit.World;

import java.time.Duration;

@Command(name = "sun")
@Permission("eternalcore.sun")
class SunCommand {

    private final NoticeService noticeService;

    @Inject
    SunCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to sun in current world")
    void sun(@Context Viewer viewer, @Context World world) {
        this.setSun(viewer, world);
    }

    @Execute
    @DescriptionDocs(description = "Sets weather to sun in specified world", arguments = "<world>")
    void sunWorld(@Context Viewer viewer, @Arg World world) {
        this.setSun(viewer, world);
        TemporalAmountParser<Duration> parser = DurationParser.TIME_UNITS;

        Duration duration = parser.parse("1d7m");
        String formatted = parser.format(duration);
    }

    private void setSun(Viewer viewer, World world) {
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
