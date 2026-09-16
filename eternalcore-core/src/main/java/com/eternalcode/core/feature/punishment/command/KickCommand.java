package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.KICK_BYPASS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentReasonValidator;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;

import net.kyori.adventure.text.Component;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "kick")
@Permission("eternalcore.kick")
@PermissionDocs(
    name = "Kick Bypass",
    permission = KICK_BYPASS,
    description = "Permission allows to bypass being kicked"
)
class KickCommand {

    private final PunishmentService punishmentService;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final PunishmentReasonValidator reasonValidator;
    private final TemplateMessageRenderer templateRenderer;
    private final Logger logger;

    @Inject
    KickCommand(
        PunishmentService punishmentService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        PunishmentReasonValidator reasonValidator,
        TemplateMessageRenderer templateRenderer,
        Logger logger
    ) {
        this.punishmentService = punishmentService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.reasonValidator = reasonValidator;
        this.templateRenderer = templateRenderer;
        this.logger = logger;
    }

    @Execute
    @DescriptionDocs(description = "Kick a player from the server", arguments = "<player> <reason>")
    void executeKick(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg Player target, @Join String reason) {
        if (!this.reasonValidator.isValid(reason)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().kickInvalidReason())
                .placeholder("{MIN}", String.valueOf(this.punishmentSettings.minReasonLength()))
                .placeholder("{MAX}", String.valueOf(this.punishmentSettings.maxReasonLength()))
                .sender(operator)
                .send();
            return;
        }

        boolean isConsole = !(operator instanceof Player);

        if (!isConsole && target.hasPermission(KICK_BYPASS)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().kickCannotKickAdmin())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        List<Component> kickMessage = this.templateRenderer.render(
            this.punishmentSettings.kickScreen(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName(),
                "{REASON}", reason
            )
        );

        this.punishmentService.kick(
                PunishmentTarget.of(target),
                PunishmentTarget.of(operator),
                reason,
                kickMessage,
                false
            )
            .thenAccept(none -> this.onSuccess(operator, target, reason, silent))
            .exceptionally(throwable -> this.onFailure(operator, "kick", throwable));
    }

    private void onSuccess(CommandSender operator, Player target, String reason, boolean silent) {
        var broadcast = this.noticeService.create()
            .notice(translation -> silent
                ? translation.punishment().kickBroadcastSilent()
                : translation.punishment().kickBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{OPERATOR}", operator.getName())
            .placeholder("{REASON}", reason);

        if (silent) {
            for (Player staff : operator.getServer().getOnlinePlayers()) {
                if (staff.hasPermission(PunishmentPermissions.STAFF_MESSAGES)) {
                    broadcast = broadcast.player(staff.getUniqueId());
                }
            }
        }
        else {
            broadcast = broadcast.all();
        }

        broadcast.send();

        this.noticeService.create()
            .notice(translation -> translation.punishment().kickSuccessPrivate())
            .placeholder("{PLAYER}", target.getName())
            .sender(operator)
            .send();
    }

    private Void onFailure(CommandSender operator, String operation, Throwable throwable) {
        this.logger.log(Level.SEVERE, "Failed to execute punishment action (" + operation + ")", throwable);

        this.noticeService.create()
            .notice(translation -> translation.punishment().punishmentActionError())
            .sender(operator)
            .send();

        return null;
    }
}
