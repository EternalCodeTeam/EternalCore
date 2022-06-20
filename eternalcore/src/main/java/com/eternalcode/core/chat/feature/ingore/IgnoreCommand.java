package com.eternalcode.core.chat.feature.ingore;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.injector.Inject;
import org.bukkit.entity.Player;

@Section(route = "ignore")
@Permission("") //TODO
public class IgnoreCommand {

    private final IgnoreRepository repository;
    private final NoticeService noticeService;

    @Inject
    public IgnoreCommand(IgnoreRepository repository, NoticeService noticeService) {
        this.repository = repository;
        this.noticeService = noticeService;
    }

    @Execute
    void ignore(Player sender, @Arg User target) {
        this.repository.ignore(sender.getUniqueId(), target.getUniqueId());
        this.noticeService.notice(); //TODO
    }

}
