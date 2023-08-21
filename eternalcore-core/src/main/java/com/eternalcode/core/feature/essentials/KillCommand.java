package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "kill")
@Permission("eternalcore.kill")
public class KillCommand {

    private final NoticeService noticeService;

    public KillCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Kill yourself")
    void execute(Player player) {
        player.setHealth(0);

        this.noticeService.create()
            .notice(translation -> translation.player().killedMessage())
            .placeholder("{PLAYER}", player.getName())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @DescriptionDocs(description = "Kill specified player", arguments = "<player>")
    void execute(Viewer audience, @Arg Player player) {
        player.setHealth(0);

        this.noticeService.create()
            .notice(translation -> translation.player().killedMessage())
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }

}
