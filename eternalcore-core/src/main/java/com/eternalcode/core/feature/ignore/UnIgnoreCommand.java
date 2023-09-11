package com.eternalcode.core.feature.ignore;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;

import java.util.UUID;

@Route(name = "unignore")
@Permission("eternalcore.ignore")
class UnIgnoreCommand {

    private final IgnoreRepository repository;
    private final NoticeService noticeService;

    @Inject
    public UnIgnoreCommand(IgnoreRepository ignoreRepository, NoticeService noticeService) {
        this.repository = ignoreRepository;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Unignore specified player", arguments = "<player>")
    void ignore(User sender, @Arg User target) {
        UUID senderUuid = sender.getUniqueId();
        UUID targetUuid = target.getUniqueId();

        if (sender.equals(target)) {
            this.noticeService.viewer(sender, translation -> translation.privateChat().cantUnIgnoreYourself());
            return;
        }

        this.repository.isIgnored(senderUuid, targetUuid).then(isIgnored -> {
            if (!isIgnored) {
                this.noticeService.create()
                    .user(sender)
                    .placeholder("{PLAYER}", target.getName())
                    .notice(translation -> translation.privateChat().notIgnorePlayer())
                    .send();

                return;
            }

            this.repository.unIgnore(senderUuid, targetUuid).then(blank -> this.noticeService.create()
                .player(senderUuid)
                .placeholder("{PLAYER}", target.getName())
                .notice(translation -> translation.privateChat().unIgnorePlayer())
                .send());
        });
    }

    @Execute(route = "-all", aliases = "*")
    @DescriptionDocs(description = "Unignore all players")
    void unIgnoreAll(User sender) {
        UUID senderUuid = sender.getUniqueId();

        this.repository.unIgnoreAll(senderUuid).then(blank -> this.noticeService.create()
            .player(senderUuid)
            .notice(translation -> translation.privateChat().unIgnoreAll())
            .send());
    }

}
