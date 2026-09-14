package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.KICKALL_BYPASS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.PunishmentReasonValidator;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;

import net.kyori.adventure.text.Component;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

@Command(name = "kickall")
@Permission("eternalcore.kickall")
@PermissionDocs(
    name = "KickAll Bypass",
    permission = KICKALL_BYPASS,
    description = "Permission allows to bypass being kicked by /kickall"
)
class KickAllCommand {

    private final PunishmentService punishmentService;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final PunishmentReasonValidator reasonValidator;
    private final TemplateMessageRenderer templateRenderer;

    @Inject
    KickAllCommand(
        PunishmentService punishmentService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        PunishmentReasonValidator reasonValidator,
        TemplateMessageRenderer templateRenderer
    ) {
        this.punishmentService = punishmentService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.reasonValidator = reasonValidator;
        this.templateRenderer = templateRenderer;
    }

    @Execute
    @DescriptionDocs(description = "Kick all online players from the server", arguments = "<reason>")
    void executeKickAll(@Sender CommandSender operator, @Join String reason) {
        if (!this.reasonValidator.isValid(reason)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().kickInvalidReason())
                .placeholder("{MIN}", String.valueOf(this.punishmentSettings.minReasonLength()))
                .placeholder("{MAX}", String.valueOf(this.punishmentSettings.maxReasonLength()))
                .sender(operator)
                .send();
            return;
        }

        PunishmentTarget operatorTarget = PunishmentTarget.of(operator);
        int kicked = this.kickEveryoneExceptBypassed(operator, operatorTarget, reason);

        this.noticeService.create()
            .notice(translation -> translation.punishment().kickAllBroadcast())
            .placeholder("{OPERATOR}", operator.getName())
            .placeholder("{REASON}", reason)
            .placeholder("{COUNT}", String.valueOf(kicked))
            .all()
            .send();
    }

    private int kickEveryoneExceptBypassed(CommandSender operator, PunishmentTarget operatorTarget, String reason) {
        int kicked = 0;

        for (Player target : operator.getServer().getOnlinePlayers()) {
            if (this.shouldSkip(operator, target)) {
                continue;
            }

            List<Component> kickMessage = this.templateRenderer.render(
                this.punishmentSettings.kickScreen(),
                Map.of(
                    "{PLAYER}", target.getName(),
                    "{OPERATOR}", operator.getName(),
                    "{REASON}", reason
                )
            );

            this.punishmentService.kick(PunishmentTarget.of(target), operatorTarget, reason, kickMessage, true);
            kicked++;
        }

        return kicked;
    }

    private boolean shouldSkip(CommandSender operator, Player target) {
        if (target.hasPermission(KICKALL_BYPASS)) {
            return true;
        }

        return operator instanceof Player operatorPlayer && operatorPlayer.getUniqueId().equals(target.getUniqueId());
    }
}
