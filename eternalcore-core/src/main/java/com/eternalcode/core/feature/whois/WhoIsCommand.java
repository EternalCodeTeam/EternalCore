package com.eternalcode.core.feature.whois;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "whois")
@Permission("eternalcore.whois")
class WhoIsCommand {

    private final NoticeService noticeService;

    @Inject
    WhoIsCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Shows information about player", arguments = "<player>")
    void execute(@Sender Viewer viewer, @Arg Player player) {
        this.noticeService.create()
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{UUID}", String.valueOf(player.getUniqueId()))
            .placeholder("{IP}", player.getAddress().getHostString())
            .placeholder("{WALK-SPEED}", String.valueOf(player.getWalkSpeed()))
            .placeholder("{SPEED}", String.valueOf(player.getFlySpeed()))
            .placeholder("{PING}", String.valueOf(player.getPing()))
            .placeholder("{LEVEL}", String.valueOf(player.getLevel()))
            .placeholder("{HEALTH}", String.valueOf(Math.round(player.getHealthScale())))
            .placeholder("{FOOD}", String.valueOf(player.getFoodLevel()))
            .messages(translation -> translation.whois().info())
            .viewer(viewer)
            .send();
    }
}
