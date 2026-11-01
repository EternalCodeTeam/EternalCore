package com.eternalcode.core.feature.weather;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Locale;
import org.bukkit.World;

@Command(name = "weather")
@Permission("eternalcore.weather")
class WeatherCommand {

    private final NoticeService noticeService;
    private final WeatherService weatherService;

    @Inject
    WeatherCommand(NoticeService noticeService, WeatherService weatherService) {
        this.noticeService = noticeService;
        this.weatherService = weatherService;
    }

    @Execute
    @DescriptionDocs(description = "Sets weather in current world", arguments = "<sun|rain|thunder> [-p]")
    void execute(@Sender Viewer viewer, @Context World world, @Arg WeatherType weatherType, @Flag("-p") boolean persistent) {
        this.setWeather(viewer, world, weatherType, persistent);
    }

    @Execute
    @DescriptionDocs(description = "Sets weather in specified world", arguments = "<sun|rain|thunder> <world> [-p]")
    void execute(
        @Sender Viewer viewer,
        @Arg WeatherType weatherType,
        @Arg World world,
        @Flag("-p") boolean persistent
    ) {
        this.setWeather(viewer, world, weatherType, persistent);
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Enables natural weather cycle in current world")
    void clearCurrentWorld(@Sender Viewer viewer, @Context World world) {
        this.clearWeather(viewer, world);
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Enables natural weather cycle in specified world", arguments = "<world>")
    void clearSelectedWorld(@Sender Viewer viewer, @Arg World world) {
        this.clearWeather(viewer, world);
    }

    private void setWeather(Viewer viewer, World world, WeatherType weatherType, boolean persistent) {
        this.weatherService.setWeather(world, weatherType, persistent);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> switch (weatherType) {
                case SUN -> translation.timeAndWeather().weatherSetSun();
                case RAIN -> translation.timeAndWeather().weatherSetRain();
                case THUNDER -> translation.timeAndWeather().weatherSetThunder();
            })
            .send();

        if (!persistent) {
            return;
        }

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .placeholder("{WEATHER}", weatherType.name().toLowerCase(Locale.ROOT))
            .notice(translation -> translation.timeAndWeather().weatherPersistenceEnabled())
            .send();
    }

    private void clearWeather(Viewer viewer, World world) {
        this.weatherService.clearPersistentWeather(world);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().weatherPersistenceDisabled())
            .send();
    }

}
