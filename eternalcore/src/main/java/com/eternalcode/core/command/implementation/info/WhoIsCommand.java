package com.eternalcode.core.command.implementation.info;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.amount.Required;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Section(route = "whois")
@Permission("eternalcore.whois")
public class WhoIsCommand {

    private final NoticeService noticeService;

    public WhoIsCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Required(1)
    public void execute(Viewer audience, @Arg Player player) {
        this.noticeService.notice()
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{UUID}", String.valueOf(player.getUniqueId()))
            .placeholder("{IP}", player.getAddress().getHostString())
            .placeholder("{WALK-SPEED}", String.valueOf(player.getWalkSpeed()))
            .placeholder("{SPEED}", String.valueOf(player.getFlySpeed()))
            .placeholder("{PING}", String.valueOf(player.getPing()))
            .placeholder("{LEVEL}", String.valueOf(player.getLevel()))
            .placeholder("{HEALTH}", String.valueOf(Math.round(player.getHealthScale())))
            .placeholder("{FOOD}", String.valueOf(player.getFoodLevel()))
            .messages(messages -> messages.other().whoisCommand())
            .viewer(audience)
            .send();
    }
}
