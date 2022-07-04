package com.eternalcode.core.chat.feature.ignore;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "unignore")
@Permission("eternalcore.ignore")
public class UnIgnoreCommand {

    private final IgnoreRepository repository;
    private final NoticeService noticeService;

    public UnIgnoreCommand(IgnoreRepository ignoreRepository, NoticeService noticeService) {
        this.repository = ignoreRepository;
        this.noticeService = noticeService;
    }

    @Execute
    void ignore(Player sender, @Arg User target) {
        this.repository.unIgnore(sender.getUniqueId(), target.getUniqueId());
        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .message(messages -> messages.privateMessage().unIgnorePlayer())
            .send();
    }

}
