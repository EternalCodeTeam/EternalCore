package com.eternalcode.core.feature.ignore;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;

import java.util.UUID;

@Command(name = "ignore")
@Permission("eternalcore.ignore")
class IgnoreCommand {

    private final IgnoreRepository repository;
    private final NoticeService noticeService;

    @Inject
    IgnoreCommand(IgnoreRepository repository, NoticeService noticeService) {
        this.repository = repository;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Ignore specified player", arguments = "<player>")
    void ignore(@Context User sender, @Arg User target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.noticeService.viewer(sender, translation -> translation.privateChat().cantIgnoreYourself());
            return;
        }

        this.repository.isIgnored(senderUuid, targetUuid).then(isIgnored -> {
            if (isIgnored) {
                this.noticeService.create()
                    .user(sender)
                    .placeholder("{PLAYER}", target.getName())
                    .notice(translation -> translation.privateChat().alreadyIgnorePlayer())
                    .send();

                return;
            }

            this.repository.ignore(senderUuid, targetUuid).then(unused -> this.noticeService.create()
                .player(senderUuid)
                .placeholder("{PLAYER}", target.getName())
                .notice(translation -> translation.privateChat().ignorePlayer())
                .send());
        });
    }

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Ignore all players")
    void ignoreAll(@Context User sender) {
        UUID senderUuid = sender.getUniqueId();

        this.repository.ignoreAll(senderUuid).then(blank -> this.noticeService.create()
            .player(senderUuid)
            .notice(translation -> translation.privateChat().ignoreAll())
            .send());
    }

}
