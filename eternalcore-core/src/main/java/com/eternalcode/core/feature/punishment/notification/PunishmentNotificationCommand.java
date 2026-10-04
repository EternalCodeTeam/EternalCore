package com.eternalcode.core.feature.punishment.notification;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.NOTIFICATIONS_TOGGLE;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.entity.Player;

@Command(name = "banmessages", aliases = { "punishmentmessages" })
@Permission(NOTIFICATIONS_TOGGLE)
class PunishmentNotificationCommand {

    private final PunishmentNotificationService notificationService;
    private final NoticeService noticeService;

    @Inject
    PunishmentNotificationCommand(PunishmentNotificationService notificationService, NoticeService noticeService) {
        this.notificationService = notificationService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Toggle punishment broadcasts about other players")
    void toggle(@Sender Player player) {
        this.update(player, !this.notificationService.isHidden(player.getUniqueId()));
    }

    @Execute(name = "on")
    @DescriptionDocs(description = "Show punishment broadcasts about other players")
    void enable(@Sender Player player) {
        this.update(player, false);
    }

    @Execute(name = "off")
    @DescriptionDocs(description = "Hide punishment broadcasts about other players (your own punishments are still shown)")
    void disable(@Sender Player player) {
        this.update(player, true);
    }

    private void update(Player player, boolean hidden) {
        this.notificationService.setHidden(player.getUniqueId(), hidden);

        this.noticeService.create()
            .notice(translation -> hidden
                ? translation.punishment().notificationsDisabled()
                : translation.punishment().notificationsEnabled())
            .player(player.getUniqueId())
            .send();
    }
}
