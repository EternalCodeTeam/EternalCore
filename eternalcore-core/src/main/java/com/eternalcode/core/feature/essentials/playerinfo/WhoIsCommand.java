package com.eternalcode.core.feature.essentials.playerinfo;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.amount.Required;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "whois")
@Permission("eternalcore.whois")
public class WhoIsCommand {

    private final NoticeService noticeService;

    @Inject
    public WhoIsCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Required(1)
    @DescriptionDocs(description = "Shows information about player", arguments = "<player>")
    void execute(Viewer viewer, @Arg Player player) {
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
            .messages(translation -> translation.player().whoisCommand())
            .viewer(viewer)
            .send();
    }
}
