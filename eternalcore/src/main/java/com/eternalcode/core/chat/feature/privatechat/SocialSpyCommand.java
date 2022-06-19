package com.eternalcode.core.chat.feature.privatechat;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

import java.util.UUID;

@Section(route = "spy", aliases = { "socialspy" })
@Permission("eternalcore.spy")
public class SocialSpyCommand {

    private final PrivateChatService privateChatService;
    private final NoticeService noticeService;

    public SocialSpyCommand(PrivateChatService privateChatService, NoticeService noticeService) {
        this.privateChatService = privateChatService;
        this.noticeService = noticeService;
    }

    @Execute
    void socialSpy(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.privateChatService.isSpy(uuid)) {
            this.privateChatService.disableSpy(uuid);
            this.noticeService.player(uuid, messages -> messages.privateMessage().socialSpyDisable());
            return;
        }

        this.noticeService.player(uuid, messages -> messages.privateMessage().socialSpyEnable());
        this.privateChatService.enableSpy(uuid);
    }

}
