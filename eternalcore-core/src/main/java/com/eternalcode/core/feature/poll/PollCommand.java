package com.eternalcode.core.feature.poll;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;

import java.time.Duration;

@Route(name = "poll")
@Permission("eternalcore.poll")
public class PollCommand {

    private final NoticeService noticeService;
    private final PollManager pollManager;

    public PollCommand(NoticeService noticeService, PollManager pollManager) {
        this.noticeService = noticeService;
        this.pollManager = pollManager;
    }

    @Execute(required = 1)
    @Route(name = "create")
    void execute(User user, @Arg Duration duration) {
        this.pollManager.createNewPoll(user, duration);
    }
}
