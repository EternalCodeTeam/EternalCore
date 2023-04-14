package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DocsDescription;
import com.eternalcode.core.command.argument.home.ArgHome;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;

@Route(name = "delhome")
public class DelHomeCommand {

    private final HomeManager homeManager;
    private final NoticeService noticeService;

    public DelHomeCommand(HomeManager homeManager, NoticeService noticeService) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
    }

    @Execute
    @DocsDescription(description = "Delete home", arguments = "<home>")
    void execute(User user, @ArgHome Home home) {
        this.homeManager.deleteHome(user, home.getName());
        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().delete())
            .placeholder("{HOME}", home.getName())
            .send();
    }

}
