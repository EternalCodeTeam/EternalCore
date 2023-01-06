package com.eternalcode.core.chat.feature.ignore;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;

import java.util.UUID;

@Route(name = "unignore")
@Permission("eternalcore.ignore")
public class UnIgnoreCommand {

    private final IgnoreRepository repository;
    private final NoticeService noticeService;

    public UnIgnoreCommand(IgnoreRepository ignoreRepository, NoticeService noticeService) {
        this.repository = ignoreRepository;
        this.noticeService = noticeService;
    }

    @Execute
    void ignore(User sender, @Arg User target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.noticeService.create()
                .notice(translation -> translation.privateChat().cantUnIgnoreYourself())
                .viewer(sender)
                .send();

            return;
        }

        this.repository.isIgnored(senderUuid, targetUuid).then(isIgnored -> {
            if (!isIgnored) {
                this.noticeService.create()
                    .user(sender)
                    .notice(translation -> translation.privateChat().notIgnorePlayer())
                    .placeholder("{PLAYER}", target.getName())
                    .send();

                return;
            }

            this.repository.unIgnore(senderUuid, targetUuid);

            this.noticeService.create()
                .player(senderUuid)
                .placeholder("{PLAYER}", target.getName())
                .notice(translation -> translation.privateChat().unIgnorePlayer())
                .send();
        });
    }

}
