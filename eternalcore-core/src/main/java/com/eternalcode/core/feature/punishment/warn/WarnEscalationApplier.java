package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.feature.punishment.ban.BanService;
import com.eternalcode.core.feature.punishment.kick.KickService;
import com.eternalcode.core.feature.punishment.mute.MuteService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Applies the automatic punishment configured in {@link PunishmentSettings#warnEscalations()}
 * once a player reaches a given number of warns.
 */
@Service
class WarnEscalationApplier {

    private static final String PLAYER_PLACEHOLDER = "{PLAYER}";
    private static final String OPERATOR_PLACEHOLDER = "{OPERATOR}";
    private static final String REASON_PLACEHOLDER = "{REASON}";
    private static final String EXPIRES_PLACEHOLDER = "{EXPIRES}";
    private static final String COUNT_PLACEHOLDER = "{COUNT}";
    private static final String ACTION_PLACEHOLDER = "{ACTION}";
    private static final boolean REMOVE_MILLIS = true;

    private final PunishmentSettings punishmentSettings;
    private final TemplateMessageRenderer templateRenderer;
    private final NoticeService noticeService;
    private final BanService banService;
    private final MuteService muteService;
    private final KickService kickService;

    @Inject
    WarnEscalationApplier(
        PunishmentSettings punishmentSettings,
        TemplateMessageRenderer templateRenderer,
        NoticeService noticeService,
        BanService banService,
        MuteService muteService,
        KickService kickService
    ) {
        this.punishmentSettings = punishmentSettings;
        this.templateRenderer = templateRenderer;
        this.noticeService = noticeService;
        this.banService = banService;
        this.muteService = muteService;
        this.kickService = kickService;
    }

    void applyIfConfigured(PunishmentTarget target, PunishmentTarget operator, int warnCount) {
        String raw = this.punishmentSettings.warnEscalations().get(warnCount);

        if (raw == null) {
            return;
        }

        WarnEscalation escalation = WarnEscalationParser.parse(raw);
        String reason = this.punishmentSettings.warnEscalationReason().replace(COUNT_PLACEHOLDER, String.valueOf(warnCount));
        Instant expiresAt = escalation.duration().map(duration -> Instant.now().plus(duration)).orElse(null);
        String expiresText = escalation.duration()
            .map(duration -> DurationUtil.format(duration, REMOVE_MILLIS))
            .orElse(this.punishmentSettings.permanentLabel());

        switch (escalation.action()) {
            case KICK -> this.kickService.kick(target, operator, reason, this.renderKickScreen(target, operator, reason), false);
            case MUTE -> this.muteService.mute(target, operator, reason, expiresAt);
            case BAN -> this.banService.ban(target, operator, reason, expiresAt, this.renderBanScreen(target, operator, reason, expiresText));
        }

        this.noticeService.create()
            .notice(translation -> translation.punishment().warnEscalationBroadcast())
            .placeholder(PLAYER_PLACEHOLDER, target.name())
            .placeholder(ACTION_PLACEHOLDER, escalation.action().name())
            .placeholder(EXPIRES_PLACEHOLDER, expiresText)
            .placeholder(COUNT_PLACEHOLDER, String.valueOf(warnCount))
            .all()
            .send();
    }

    private List<Component> renderKickScreen(PunishmentTarget target, PunishmentTarget operator, String reason) {
        return this.templateRenderer.render(
            this.punishmentSettings.kickScreen(),
            Map.of(
                PLAYER_PLACEHOLDER, target.name(),
                OPERATOR_PLACEHOLDER, operator.name(),
                REASON_PLACEHOLDER, reason
            )
        );
    }

    private List<Component> renderBanScreen(PunishmentTarget target, PunishmentTarget operator, String reason, String expiresText) {
        return this.templateRenderer.render(
            this.punishmentSettings.banKickScreen(),
            Map.of(
                PLAYER_PLACEHOLDER, target.name(),
                OPERATOR_PLACEHOLDER, operator.name(),
                REASON_PLACEHOLDER, reason,
                EXPIRES_PLACEHOLDER, expiresText
            )
        );
    }
}
