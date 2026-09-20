package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
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

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "unmute")
@Permission("eternalcore.unmute")
class UnmuteCommand {

    private final PunishmentService punishmentService;
    private final NoticeService noticeService;
    private final PunishmentBroadcastService broadcastService;
    private final Logger logger;

    @Inject
    UnmuteCommand(
        PunishmentService punishmentService,
        NoticeService noticeService,
        PunishmentBroadcastService broadcastService,
        Logger logger
    ) {
        this.punishmentService = punishmentService;
        this.noticeService = noticeService;
        this.broadcastService = broadcastService;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Unmute a player", arguments = "<player>")
    void execute(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg OfflinePlayer target) {
        if (!this.punishmentService.isMuted(target.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().unmuteNotMuted())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        try {
            this.punishmentService.unmute(PunishmentTarget.of(target), PunishmentTarget.of(operator));
            this.onSuccess(operator, target, silent);
        }
        catch (Exception exception) {
            this.logger.log(Level.SEVERE, "Failed to execute punishment action (unmute)", exception);

            this.noticeService.create()
                .notice(translation -> translation.punishment().punishmentActionError())
                .sender(operator)
                .send();
        }
    }

    private void onSuccess(CommandSender operator, OfflinePlayer target, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().unmuteBroadcastSilent() : translation.punishment().unmuteBroadcast(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName()
            ),
            silent,
            PunishmentPermissions.STAFF_MESSAGES
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().unmuteSuccessPrivate(),
            Map.of("{PLAYER}", target.getName()),
            operator
        );
    }
}
