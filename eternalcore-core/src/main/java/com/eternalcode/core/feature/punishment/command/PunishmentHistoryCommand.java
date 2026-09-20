package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.HISTORY_SELF;
import static com.eternalcode.core.feature.punishment.PunishmentPermissions.HISTORY_STAFF;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.util.date.DateFormatter;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "punishmenthistory", aliases = { "history" })
class PunishmentHistoryCommand {

    private static final int FIRST_PAGE = 1;

    private final PunishmentHistoryService punishmentHistoryService;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final DateFormatter dateFormatter;
    private final Logger logger;

    @Inject
    PunishmentHistoryCommand(
        PunishmentHistoryService punishmentHistoryService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        DateFormatter dateFormatter,
        Logger logger
    ) {
        this.punishmentHistoryService = punishmentHistoryService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.dateFormatter = dateFormatter;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Shows recent punishments across the server")
    void executeRecent(@Sender CommandSender operator) {
        if (!operator.hasPermission(HISTORY_STAFF)) {
            this.sendNoPermission(operator);
            return;
        }

        this.showRecent(operator, FIRST_PAGE);
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Shows recent punishments across the server, at a specific page", arguments = "<page>")
    void executeRecentPage(@Sender CommandSender operator, @Arg int page) {
        if (!operator.hasPermission(HISTORY_STAFF)) {
            this.sendNoPermission(operator);
            return;
        }

        this.showRecent(operator, page);
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Shows punishment history for a specific player (staff only, unless viewing your own with eternalcore.history.self)", arguments = "<player>")
    void executeForPlayer(@Sender CommandSender operator, @Arg OfflinePlayer target) {
        if (!this.canView(operator, target)) {
            this.sendNoPermission(operator);
            return;
        }

        this.showForPlayer(operator, target, FIRST_PAGE);
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Shows punishment history for a specific player, at a specific page", arguments = "<player> <page>")
    void executeForPlayerPage(@Sender CommandSender operator, @Arg OfflinePlayer target, @Arg int page) {
        if (!this.canView(operator, target)) {
            this.sendNoPermission(operator);
            return;
        }

        this.showForPlayer(operator, target, page);
    }

    private boolean canView(CommandSender operator, OfflinePlayer target) {
        if (operator.hasPermission(HISTORY_STAFF)) {
            return true;
        }

        return operator instanceof Player player
            && player.getUniqueId().equals(target.getUniqueId())
            && player.hasPermission(HISTORY_SELF);
    }

    private void sendNoPermission(CommandSender operator) {
        this.noticeService.create()
            .notice(translation -> translation.punishment().historyNoPermission())
            .sender(operator)
            .send();
    }

    private void showRecent(CommandSender operator, int humanPage) {
        int page = this.toZeroIndexed(humanPage);

        try {
            List<PunishmentHistoryEntry> entries = this.punishmentHistoryService.findRecent(page, 10);

            this.noticeService.create()
                .notice(translation -> translation.punishment().historyHeaderRecent())
                .placeholder("{PAGE}", String.valueOf(humanPage))
                .sender(operator)
                .send();

            this.send(operator, entries);
        }
        catch (Exception exception) {
            this.handleFailure(operator, "findRecent", exception);
        }
    }

    private void showForPlayer(CommandSender operator, OfflinePlayer target, int humanPage) {
        int page = this.toZeroIndexed(humanPage);

        try {
            List<PunishmentHistoryEntry> entries = this.punishmentHistoryService.findByTarget(target.getUniqueId(), page, 10);

            this.noticeService.create()
                .notice(translation -> translation.punishment().historyHeaderPlayer())
                .placeholder("{PLAYER}", target.getName())
                .placeholder("{PAGE}", String.valueOf(humanPage))
                .sender(operator)
                .send();

            this.send(operator, entries);
        }
        catch (Exception exception) {
            this.handleFailure(operator, "findByTarget", exception);
        }
    }

    private void handleFailure(CommandSender operator, String operation, Throwable throwable) {
        this.logger.log(Level.SEVERE, "Failed to load punishment history (" + operation + ")", throwable);

        this.noticeService.create()
            .notice(translation -> translation.punishment().historyError())
            .sender(operator)
            .send();
    }

    private int toZeroIndexed(int humanPage) {
        int clamped = Math.max(humanPage, FIRST_PAGE);
        return clamped - 1;
    }

    private void send(CommandSender operator, List<PunishmentHistoryEntry> entries) {
        if (entries.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().historyEmpty())
                .sender(operator)
                .send();
            return;
        }

        for (PunishmentHistoryEntry entry : entries) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().historyEntry())
                .placeholder("{DATE}", this.dateFormatter.format(entry.timestamp()))
                .placeholder("{ACTION}", entry.action().name())
                .placeholder("{PLAYER}", entry.target().name())
                .placeholder("{OPERATOR}", entry.operator().name())
                .placeholder("{REASON}", entry.reason())
                .placeholder("{EXPIRES}", this.formatExpires(entry))
                .sender(operator)
                .send();
        }
    }

    private String formatExpires(PunishmentHistoryEntry entry) {
        Instant expiresAt = entry.expiresAt();

        return expiresAt == null
            ? this.punishmentSettings.permanentLabel()
            : DurationUtil.format(Duration.between(entry.timestamp(), expiresAt), true);
    }
}
