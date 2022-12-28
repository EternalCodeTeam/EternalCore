package com.eternalcode.core.chat.feature.ignore;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.injector.Inject;

import java.util.UUID;

@Route(name = "ignore")
@Permission("eternalcore.ignore")
public class IgnoreCommand {

    private final IgnoreRepository repository;
    private final NoticeService noticeService;

    @Inject
    public IgnoreCommand(IgnoreRepository repository, NoticeService noticeService) {
        this.repository = repository;
        this.noticeService = noticeService;
    }

    @Execute
    void ignore(User sender, @Arg User target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        this.repository.isIgnored(senderUuid, targetUuid).then(isIgnored -> {
            if (isIgnored) {
                this.noticeService.create()
                    .user(sender)
                    .notice(messages -> messages.privateMessage().alreadyIgnorePlayer())
                    .placeholder("{PLAYER}", target.getName())
                    .send();

                return;
            }

            if (sender.equals(target)) {
                this.noticeService.create()
                    .notice(messages -> messages.privateMessage().cantIgnoreYourself())
                    .viewer(sender)
                    .send();

                return;
            }

            this.repository.ignore(senderUuid, targetUuid);

            this.noticeService.create()
                .player(senderUuid)
                .placeholder("{PLAYER}", target.getName())
                .notice(messages -> messages.privateMessage().ignorePlayer())
                .send();
        });
    }

}
