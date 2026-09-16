package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.ip.AltAccount;
import com.eternalcode.core.ip.PlayerIpService;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Command(name = "altaccounts", aliases = { "alts" })
@Permission("eternalcore.altaccounts")
class AltAccountsCommand {

    private final PlayerIpService playerIpService;
    private final NoticeService noticeService;
    private final Logger logger;

    @Inject
    AltAccountsCommand(PlayerIpService playerIpService, NoticeService noticeService, Logger logger) {
        this.playerIpService = playerIpService;
        this.noticeService = noticeService;
        this.logger = logger;
    }

    @Execute
    @DescriptionDocs(description = "Show accounts that share an IP address with the given player", arguments = "<player>")
    void execute(@Sender CommandSender operator, @Arg OfflinePlayer target) {
        this.playerIpService.findAltAccounts(target.getUniqueId())
            .thenAccept(alts -> {
                if (alts.isEmpty()) {
                    this.noticeService.create()
                        .notice(translation -> translation.punishment().altAccountsNone())
                        .placeholder("{PLAYER}", target.getName())
                        .sender(operator)
                        .send();
                    return;
                }

                String accounts = alts.stream()
                    .map(AltAccount::name)
                    .collect(Collectors.joining(", "));

                this.noticeService.create()
                    .notice(translation -> translation.punishment().altAccountsFound())
                    .placeholder("{PLAYER}", target.getName())
                    .placeholder("{ACCOUNTS}", accounts)
                    .sender(operator)
                    .send();
            })
            .exceptionally(throwable -> {
                this.logger.log(Level.SEVERE, "Failed to look up alt accounts for " + target.getName(), throwable);

                this.noticeService.create()
                    .notice(translation -> translation.punishment().punishmentActionError())
                    .sender(operator)
                    .send();

                return null;
            });
    }
}
