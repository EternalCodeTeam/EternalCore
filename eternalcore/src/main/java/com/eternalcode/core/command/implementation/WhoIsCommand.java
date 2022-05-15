package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;

@Section(route = "whois")
@Permission("eternalcore.command.whois")
public class WhoIsCommand {

    private final NoticeService noticeService;

    public WhoIsCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Required(1)
    public void execute(Audience audience, @Arg(0) Player player) {
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
            .audience(audience)
            .send();
    }
}
