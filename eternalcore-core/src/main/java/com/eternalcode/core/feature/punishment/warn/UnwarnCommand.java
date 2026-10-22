package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentBroadcastService;
import com.eternalcode.core.feature.punishment.PunishmentPartyFactory;
import com.eternalcode.core.feature.punishment.PunishmentPermissionConstant;
import com.eternalcode.core.feature.punishment.command.argument.WarnedPlayerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "unwarn")
@Permission("eternalcore.unwarn")
class UnwarnCommand {

    private final WarnService warnService;
    private final NoticeService noticeService;
    private final PunishmentBroadcastService broadcastService;
    private final Logger logger;

    @Inject
    UnwarnCommand(
        WarnService warnService,
        NoticeService noticeService,
        PunishmentBroadcastService broadcastService,
        Logger logger
    ) {
        this.warnService = warnService;
        this.noticeService = noticeService;
        this.broadcastService = broadcastService;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Revoke the latest active warn of a player", arguments = "[-s] <player>")
    void execute(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg(WarnedPlayerArgument.KEY) OfflinePlayer target) {
        Optional<Warn> latestWarn = this.warnService.activeWarns().stream()
            .filter(warn -> warn.target().uuid().equals(target.getUniqueId()))
            .max(Comparator.comparing(Warn::createdAt));

        if (latestWarn.isEmpty()) {
            this.sendNotWarned(operator, target);
            return;
        }

        try {
            boolean revoked = this.warnService.revokeWarn(latestWarn.get().id(), PunishmentPartyFactory.actor(operator));

            if (!revoked) {
                this.sendNotWarned(operator, target);
                return;
            }

            this.onSuccess(operator, target, silent);
        }
        catch (Exception exception) {
            this.logger.log(Level.SEVERE, "Failed to execute punishment action (unwarn)", exception);

            this.noticeService.create()
                .notice(translation -> translation.punishment().punishmentActionError())
                .sender(operator)
                .send();
        }
    }

    private void sendNotWarned(CommandSender operator, OfflinePlayer target) {
        this.noticeService.create()
            .notice(translation -> translation.punishment().unwarnNotWarned())
            .placeholder("{PLAYER}", target.getName())
            .sender(operator)
            .send();
    }

    private void onSuccess(CommandSender operator, OfflinePlayer target, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().unwarnBroadcastSilent() : translation.punishment().unwarnBroadcast(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName()
            ),
            silent,
            PunishmentPermissionConstant.STAFF_MESSAGES,
            target.getUniqueId()
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().unwarnSuccessPrivate(),
            Map.of("{PLAYER}", target.getName()),
            operator
        );
    }
}
