package com.eternalcode.core.feature.punishment.kick;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.KICK_BYPASS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.DurationReasonParser;
import com.eternalcode.core.feature.punishment.PunishmentBroadcastService;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
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

    private final KickService kickService;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final PunishmentBroadcastService broadcastService;
    private final TemplateMessageRenderer templateRenderer;
    private final Logger logger;

    @Inject
    KickCommand(
        KickService kickService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        PunishmentBroadcastService broadcastService,
        TemplateMessageRenderer templateRenderer,
        Logger logger
    ) {
        this.kickService = kickService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.broadcastService = broadcastService;
        this.templateRenderer = templateRenderer;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Kick a player from the server", arguments = "[-s] <player> [reason]")
    void executeKick(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg Player target, @Join @OptionalArg String rawReason) {
        String reason = DurationReasonParser.reasonOrDefault(rawReason, this.punishmentSettings.defaultReason());
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

        try {
            this.kickService.kick(
                PunishmentTarget.of(target),
                PunishmentTarget.of(operator),
                reason,
                kickMessage,
                false
            );

            this.onSuccess(operator, target, reason, silent);
        }
        catch (Exception exception) {
            this.onFailure(operator, "kick", exception);
        }
    }

    private void onSuccess(CommandSender operator, Player target, String reason, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().kickBroadcastSilent() : translation.punishment().kickBroadcast(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName(),
                "{REASON}", reason
            ),
            silent,
            PunishmentPermissions.STAFF_MESSAGES,
            target.getUniqueId()
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().kickSuccessPrivate(),
            Map.of("{PLAYER}", target.getName()),
            operator
        );
    }

    private void onFailure(CommandSender operator, String operation, Throwable throwable) {
        this.logger.log(Level.SEVERE, "Failed to execute punishment action (" + operation + ")", throwable);

        this.noticeService.create()
            .notice(translation -> translation.punishment().punishmentActionError())
            .sender(operator)
            .send();
    }
}
