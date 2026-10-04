package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.feature.punishment.notification.PunishmentNotificationService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;

import com.eternalcode.multification.notice.provider.NoticeProvider;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@Service
public class PunishmentBroadcastService {

    private final NoticeService noticeService;
    private final PunishmentNotificationService notificationService;
    private final Server server;

    @Inject
    public PunishmentBroadcastService(
        NoticeService noticeService,
        PunishmentNotificationService notificationService,
        Server server
    ) {
        this.noticeService = noticeService;
        this.notificationService = notificationService;
        this.server = server;
    }

    public void broadcast(
        NoticeProvider<Translation> broadcastTranslation,
        Map<String, String> placeholders,
        boolean silent,
        String staffPermission
    ) {
        this.broadcast(broadcastTranslation, placeholders, silent, staffPermission, null);
    }

    /**
     * @param affectedPlayer player the broadcast is about - always receives it, even with notifications hidden. May be null.
     */
    public void broadcast(
        NoticeProvider<Translation> broadcastTranslation,
        Map<String, String> placeholders,
        boolean silent,
        String staffPermission,
        UUID affectedPlayer
    ) {
        var notice = this.noticeService.create().notice(broadcastTranslation);

        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            notice = notice.placeholder(placeholder.getKey(), placeholder.getValue());
        }

        var recipients = notice.console();

        for (Player player : this.server.getOnlinePlayers()) {
            if (this.shouldReceive(player, silent, staffPermission, affectedPlayer)) {
                recipients = recipients.player(player.getUniqueId());
            }
        }

        recipients.send();
    }

    private boolean shouldReceive(Player player, boolean silent, String staffPermission, UUID affectedPlayer) {
        if (silent && !player.hasPermission(staffPermission)) {
            return false;
        }

        if (player.getUniqueId().equals(affectedPlayer)) {
            return true;
        }

        return !this.notificationService.isHidden(player.getUniqueId());
    }

    public void privateConfirmation(
        NoticeProvider<Translation> privateTranslation,
        Map<String, String> placeholders,
        CommandSender operator
    ) {
        var notice = this.noticeService.create().notice(privateTranslation);

        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            notice = notice.placeholder(placeholder.getKey(), placeholder.getValue());
        }

        notice.sender(operator).send();
    }
}
