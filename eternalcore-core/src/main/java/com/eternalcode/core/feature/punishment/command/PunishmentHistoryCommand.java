package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
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

import java.util.List;

@Command(name = "punishmenthistory", aliases = { "history" })
@Permission("eternalcore.punishmenthistory")
class PunishmentHistoryCommand {

    private final PunishmentHistoryService punishmentHistoryService;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final DateFormatter dateFormatter;

    @Inject
    PunishmentHistoryCommand(
        PunishmentHistoryService punishmentHistoryService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        DateFormatter dateFormatter
    ) {
        this.punishmentHistoryService = punishmentHistoryService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.dateFormatter = dateFormatter;
    }

    @Execute
    @DescriptionDocs(description = "Shows recent punishments across the server", arguments = "[page]")
    void executeRecent(@Sender CommandSender operator, @Arg(value = "page") int page) {
        this.punishmentHistoryService.findRecent(page, this.punishmentSettings.historyPageSize())
            .thenAccept(entries -> {
                this.noticeService.create()
                    .notice(translation -> translation.punishment().historyHeaderRecent())
                    .placeholder("{PAGE}", String.valueOf(page))
                    .sender(operator)
                    .send();

                this.send(operator, entries);
            });
    }

    @Execute
    @DescriptionDocs(description = "Shows punishment history for a specific player", arguments = "<player> [page]")
    void executeForPlayer(@Sender CommandSender operator, @Arg OfflinePlayer target, @Arg(value = "page") int page) {
        this.punishmentHistoryService.findByTarget(target.getUniqueId(), page, this.punishmentSettings.historyPageSize())
            .thenAccept(entries -> {
                this.noticeService.create()
                    .notice(translation -> translation.punishment().historyHeaderPlayer())
                    .placeholder("{PLAYER}", target.getName())
                    .placeholder("{PAGE}", String.valueOf(page))
                    .sender(operator)
                    .send();

                this.send(operator, entries);
            });
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
