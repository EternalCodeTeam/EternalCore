package com.eternalcode.core.command.implementation.weather;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Route(name = "thunder", aliases = "storm")
@Permission("eternalcore.thunder")
public class ThunderCommand {

    private final NoticeService noticeService;

    public ThunderCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void thunder(Player player, Viewer viewer) {
        this.thunder(viewer, player.getWorld());
    }

    @Execute(required = 1)
    void thunder(Viewer viewer, @Arg World world) {
        world.setStorm(true);
        world.setThundering(true);

        this.noticeService.viewer(viewer, messages -> messages.timeAndWeather().weatherSetThunder());
    }

}
