package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.HISTORY_SELF;
import static com.eternalcode.core.feature.punishment.PunishmentPermissions.HISTORY_STAFF;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.gui.PlayerPunishmentHistoryGui;
import com.eternalcode.core.feature.punishment.gui.PunishmentHistoryGui;
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
    private static final int TEXT_PAGE_SIZE = 10;
    private static final boolean REMOVE_MILLIS = true;

    private static final String PAGE_PLACEHOLDER = "{PAGE}";
    private static final String PLAYER_PLACEHOLDER = "{PLAYER}";
    private static final String DATE_PLACEHOLDER = "{DATE}";
    private static final String ACTION_PLACEHOLDER = "{ACTION}";
    private static final String OPERATOR_PLACEHOLDER = "{OPERATOR}";
    private static final String REASON_PLACEHOLDER = "{REASON}";
    private static final String EXPIRES_PLACEHOLDER = "{EXPIRES}";

    private final PunishmentHistoryService punishmentHistoryService;
    private final PunishmentHistoryGui punishmentHistoryGui;
    private final PlayerPunishmentHistoryGui playerPunishmentHistoryGui;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final DateFormatter dateFormatter;
    private final Logger logger;

    @Inject
    PunishmentHistoryCommand(
        PunishmentHistoryService punishmentHistoryService,
        PunishmentHistoryGui punishmentHistoryGui,
        PlayerPunishmentHistoryGui playerPunishmentHistoryGui,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        DateFormatter dateFormatter,
        Logger logger
    ) {
        this.punishmentHistoryService = punishmentHistoryService;
        this.punishmentHistoryGui = punishmentHistoryGui;
        this.playerPunishmentHistoryGui = playerPunishmentHistoryGui;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.dateFormatter = dateFormatter;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Opens punishment history GUI (staff: recent punishments, eternalcore.history.self: own history). Console gets text output.")
    void executeRecent(@Sender CommandSender sender) {
        if (sender instanceof Player viewer) {
            this.openRecentGui(viewer);
            return;
        }

        this.showRecentText(sender, FIRST_PAGE);
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Shows recent punishments at a specific page (console text). Players get the GUI.", arguments = "<page>")
    void executeRecentPage(@Sender CommandSender sender, @Arg int page) {
        if (sender instanceof Player viewer) {
            this.openRecentGui(viewer);
            return;
        }

        this.showRecentText(sender, page);
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Opens punishment history GUI of a player (staff only; eternalcore.history.self always opens own history). Console gets text output.", arguments = "<player>")
    void executeForPlayer(@Sender CommandSender sender, @Arg OfflinePlayer target) {
        if (sender instanceof Player viewer) {
            this.openPlayerGui(viewer, target);
            return;
        }

        this.showForPlayerText(sender, target, FIRST_PAGE);
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Shows punishment history of a player at a specific page (console text). Players get the GUI.", arguments = "<player> <page>")
    void executeForPlayerPage(@Sender CommandSender sender, @Arg OfflinePlayer target, @Arg int page) {
        if (sender instanceof Player viewer) {
            this.openPlayerGui(viewer, target);
            return;
        }

        this.showForPlayerText(sender, target, page);
    }

    private void openRecentGui(Player viewer) {
        if (viewer.hasPermission(HISTORY_STAFF)) {
            this.punishmentHistoryGui.open(viewer);
            return;
        }

        this.openOwnGuiOrDeny(viewer);
    }

    private void openPlayerGui(Player viewer, OfflinePlayer target) {
        if (viewer.hasPermission(HISTORY_STAFF)) {
            this.playerPunishmentHistoryGui.open(viewer, target.getUniqueId(), this.nameOf(target));
            return;
        }

        this.openOwnGuiOrDeny(viewer);
    }

    private void openOwnGuiOrDeny(Player viewer) {
        if (!viewer.hasPermission(HISTORY_SELF)) {
            this.sendNoPermission(viewer);
            return;
        }

        this.playerPunishmentHistoryGui.open(viewer, viewer.getUniqueId(), viewer.getName());
    }

    private void showRecentText(CommandSender sender, int humanPage) {
        if (!sender.hasPermission(HISTORY_STAFF)) {
            this.sendNoPermission(sender);
            return;
        }

        int page = this.toZeroIndexed(humanPage);

        try {
            List<PunishmentHistoryEntry> entries = this.punishmentHistoryService.findRecent(page, TEXT_PAGE_SIZE);

            this.noticeService.create()
                .notice(translation -> translation.punishment().historyHeaderRecent())
                .placeholder(PAGE_PLACEHOLDER, String.valueOf(humanPage))
                .sender(sender)
                .send();

            this.sendEntries(sender, entries);
        }
        catch (Exception exception) {
            this.handleFailure(sender, "findRecent", exception);
        }
    }

    private void showForPlayerText(CommandSender sender, OfflinePlayer target, int humanPage) {
        if (!sender.hasPermission(HISTORY_STAFF)) {
            this.sendNoPermission(sender);
            return;
        }

        int page = this.toZeroIndexed(humanPage);

        try {
            List<PunishmentHistoryEntry> entries = this.punishmentHistoryService.findByTarget(target.getUniqueId(), page, TEXT_PAGE_SIZE);

            this.noticeService.create()
                .notice(translation -> translation.punishment().historyHeaderPlayer())
                .placeholder(PLAYER_PLACEHOLDER, this.nameOf(target))
                .placeholder(PAGE_PLACEHOLDER, String.valueOf(humanPage))
                .sender(sender)
                .send();

            this.sendEntries(sender, entries);
        }
        catch (Exception exception) {
            this.handleFailure(sender, "findByTarget", exception);
        }
    }

    private void sendEntries(CommandSender sender, List<PunishmentHistoryEntry> entries) {
        if (entries.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().historyEmpty())
                .sender(sender)
                .send();
            return;
        }

        for (PunishmentHistoryEntry entry : entries) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().historyEntry())
                .placeholder(DATE_PLACEHOLDER, this.dateFormatter.format(entry.timestamp()))
                .placeholder(ACTION_PLACEHOLDER, entry.action().name())
                .placeholder(PLAYER_PLACEHOLDER, entry.target().name())
                .placeholder(OPERATOR_PLACEHOLDER, entry.operator().name())
                .placeholder(REASON_PLACEHOLDER, entry.reason())
                .placeholder(EXPIRES_PLACEHOLDER, this.formatExpires(entry))
                .sender(sender)
                .send();
        }
    }

    private void sendNoPermission(CommandSender sender) {
        this.noticeService.create()
            .notice(translation -> translation.punishment().historyNoPermission())
            .sender(sender)
            .send();
    }

    private void handleFailure(CommandSender sender, String operation, Throwable throwable) {
        this.logger.log(Level.SEVERE, "Failed to load punishment history (" + operation + ")", throwable);

        this.noticeService.create()
            .notice(translation -> translation.punishment().historyError())
            .sender(sender)
            .send();
    }

    private String nameOf(OfflinePlayer target) {
        String name = target.getName();
        return name != null ? name : target.getUniqueId().toString();
    }

    private int toZeroIndexed(int humanPage) {
        int clamped = Math.max(humanPage, FIRST_PAGE);
        return clamped - FIRST_PAGE;
    }

    private String formatExpires(PunishmentHistoryEntry entry) {
        Instant expiresAt = entry.expiresAt();

        return expiresAt == null
            ? this.punishmentSettings.permanentLabel()
            : DurationUtil.format(Duration.between(entry.timestamp(), expiresAt), REMOVE_MILLIS);
    }
}
