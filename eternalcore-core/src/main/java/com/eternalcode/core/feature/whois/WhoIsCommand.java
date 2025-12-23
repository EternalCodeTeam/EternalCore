package com.eternalcode.core.feature.whois;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.util.date.DateFormatter;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "whois")
@Permission("eternalcore.whois")
class WhoIsCommand {

    private final NoticeService noticeService;
    private final UserManager userManager;
    private final DateFormatter dateFormatter;

    @Inject
    WhoIsCommand(NoticeService noticeService, UserManager userManager, DateFormatter dateFormatter) {
        this.noticeService = noticeService;
        this.userManager = userManager;
        this.dateFormatter = dateFormatter;
    }

    @Execute
    @DescriptionDocs(description = "Shows information about player", arguments = "<player>")
    void execute(@Sender Viewer viewer, @Arg Player player) {
        this.userManager.findOrCreate(player.getUniqueId(), player.getName())
            .thenAccept(user -> this.noticeService.create()
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{UUID}", String.valueOf(player.getUniqueId()))
                .placeholder("{IP}", player.getAddress().getHostString())
                .placeholder("{WALK-SPEED}", String.valueOf(player.getWalkSpeed()))
                .placeholder("{SPEED}", String.valueOf(player.getFlySpeed()))
                .placeholder("{PING}", String.valueOf(player.getPing()))
                .placeholder("{LEVEL}", String.valueOf(player.getLevel()))
                .placeholder("{HEALTH}", String.valueOf(Math.round(player.getHealthScale())))
                .placeholder("{FOOD}", String.valueOf(player.getFoodLevel()))
                .placeholder("{LAST-SEEN}", this.dateFormatter.format(user.getLastSeen()))
                .placeholder("{ACCOUNT-CREATED}", this.dateFormatter.format(user.getAccountCreated()))
                .messages(translation -> translation.whois().info())
                .viewer(viewer)
                .send());
    }
}
