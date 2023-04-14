package com.eternalcode.core.feature.essentials.time;

import com.eternalcode.annotations.scan.command.DocsDescription;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;

@Route(name = "night")
@Permission("eternalcore.night")
public class NightCommand {

    private final NoticeService noticeService;

    public NightCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DocsDescription(description = "Sets time to night in specified world", arguments = "<world>")
    void night(Viewer viewer, @Arg World world) {
        world.setTime(13700);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(translation -> translation.timeAndWeather().timeSetNight())
            .send();
    }

}
