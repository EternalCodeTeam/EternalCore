package com.eternalcode.core.command.implementation.time;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Route(name = "night")
@Permission("eternalcore.night")
public class NightCommand {

    private final NoticeService noticeService;

    public NightCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void night(Player player, Viewer viewer) {
        this.night(viewer, player.getWorld());
    }

    @Execute(required = 1)
    void night(Viewer viewer, @Arg World world) {
        world.setTime(13700);

        this.noticeService.create()
            .viewer(viewer)
            .placeholder("{WORLD}", world.getName())
            .notice(messages -> messages.timeAndWeather().timeSetNight())
            .send();
    }

}
