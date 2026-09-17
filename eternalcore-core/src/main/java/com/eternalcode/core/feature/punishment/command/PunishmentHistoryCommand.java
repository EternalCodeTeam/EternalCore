package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.gui.PlayerPunishmentHistoryGui;
import com.eternalcode.core.feature.punishment.gui.PunishmentHistoryGui;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.date.DateFormatter;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "punishmenthistory", aliases = { "history" })
@Permission("eternalcore.punishmenthistory")
class PunishmentHistoryCommand {

    private static final int FIRST_PAGE = 1;

    private final PunishmentHistoryService punishmentHistoryService;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final DateFormatter dateFormatter;
    private final Logger logger;
    private final PunishmentHistoryGui punishmentHistoryGui;
    private final PlayerPunishmentHistoryGui playerPunishmentHistoryGui;

    @Inject
    PunishmentHistoryCommand(
        PunishmentHistoryService punishmentHistoryService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        DateFormatter dateFormatter,
        Logger logger,
        PunishmentHistoryGui punishmentHistoryGui,
        PlayerPunishmentHistoryGui playerPunishmentHistoryGui
    ) {
        this.punishmentHistoryService = punishmentHistoryService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.dateFormatter = dateFormatter;
        this.logger = logger;
        this.punishmentHistoryGui = punishmentHistoryGui;
        this.playerPunishmentHistoryGui = playerPunishmentHistoryGui;
    }

    @Execute
    @DescriptionDocs(description = "Shows recent punishments across the server")
    void executeRecent(@Sender CommandSender operator) {
        this.showRecent(operator, FIRST_PAGE);
    }

    @Execute
    @DescriptionDocs(description = "Shows recent punishments across the server, at a specific page", arguments = "<page>")
    void executeRecentPage(@Sender CommandSender operator, @Arg int page) {
        this.showRecent(operator, page);
    }

    @Execute
    @DescriptionDocs(description = "Shows punishment history for a specific player", arguments = "<player>")
    void executeForPlayer(@Sender CommandSender operator, @Arg OfflinePlayer target) {
        this.showForPlayer(operator, target, FIRST_PAGE);
    }

    @Execute
    @DescriptionDocs(description = "Shows punishment history for a specific player, at a specific page", arguments = "<player> <page>")
    void executeForPlayerPage(@Sender CommandSender operator, @Arg OfflinePlayer target, @Arg int page) {
        this.showForPlayer(operator, target, page);
    }

    private void showRecent(CommandSender operator, int humanPage) {
        if (operator instanceof Player player) {
            this.punishmentHistoryGui.open(player);
            return;
        }

        int page = this.toZeroIndexed(humanPage);

        this.punishmentHistoryService.findRecent(page, this.punishmentSettings.historyPageSize())
            .thenAccept(entries -> {
                this.noticeService.create()
                    .notice(translation -> translation.punishment().historyHeaderRecent())
                    .placeholder("{PAGE}", String.valueOf(humanPage))
                    .sender(operator)
                    .send();

                this.send(operator, entries);
            })
            .exceptionally(throwable -> this.handleFailure(operator, "findRecent", throwable));
    }

    private void showForPlayer(CommandSender operator, OfflinePlayer target, int humanPage) {
        if (operator instanceof Player player) {
            this.playerPunishmentHistoryGui.open(player, target.getUniqueId(), target.getName());
            return;
        }

        int page = this.toZeroIndexed(humanPage);

        this.punishmentHistoryService.findByTarget(target.getUniqueId(), page, this.punishmentSettings.historyPageSize())
            .thenAccept(entries -> {
                this.noticeService.create()
                    .notice(translation -> translation.punishment().historyHeaderPlayer())
                    .placeholder("{PLAYER}", target.getName())
                    .placeholder("{PAGE}", String.valueOf(humanPage))
                    .sender(operator)
                    .send();

                this.send(operator, entries);
            })
            .exceptionally(throwable -> this.handleFailure(operator, "findByTarget", throwable));
    }

    private Void handleFailure(CommandSender operator, String operation, Throwable throwable) {
        this.logger.log(Level.SEVERE, "Failed to load punishment history (" + operation + ")", throwable);

        this.noticeService.create()
            .notice(translation -> translation.punishment().historyError())
            .sender(operator)
            .send();

        return null;
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
                .sender(operator)
                .send();
        }
    }
}
