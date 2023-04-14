package com.eternalcode.core.feature.ignore;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notification.NoticeService;
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
    @DescriptionDocs(description = "Ignore specified player", arguments = "<player>")
    void ignore(User sender, @Arg User target) {
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

    @Execute(route = "-all", aliases = "*")
    @DescriptionDocs(description = "Ignore all players")
    void ignoreAll(User sender) {
        UUID senderUuid = sender.getUniqueId();

        this.repository.ignoreAll(senderUuid).then(blank -> this.noticeService.create()
            .player(senderUuid)
            .notice(translation -> translation.privateChat().ignoreAll())
            .send());
    }

}
