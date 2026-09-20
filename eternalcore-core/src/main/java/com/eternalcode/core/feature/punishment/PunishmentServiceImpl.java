package com.eternalcode.core.feature.punishment;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry.HistoryAction;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;
import com.eternalcode.core.feature.punishment.warn.WarnEscalation;
import com.eternalcode.core.feature.punishment.warn.WarnEscalationParser;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import java.util.Objects;
import java.util.Optional;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
class PunishmentServiceImpl implements PunishmentService {

    private final Map<UUID, Punishment> activeBans = new ConcurrentHashMap<>();
    private final Map<UUID, Punishment> activeMutes = new ConcurrentHashMap<>();
    private final Map<UUID, Punishment> activeWarns = new ConcurrentHashMap<>();

    private final PunishmentRepository punishmentRepository;
    private final PunishmentHistoryService punishmentHistoryService;
    private final PunishmentSettings punishmentSettings;
    private final TemplateMessageRenderer templateRenderer;
    private final NoticeService noticeService;
    private final Server server;
    private final Scheduler scheduler;

    @Inject
    PunishmentServiceImpl(
        PunishmentRepository punishmentRepository,
        PunishmentHistoryService punishmentHistoryService,
        PunishmentSettings punishmentSettings,
        TemplateMessageRenderer templateRenderer,
        NoticeService noticeService,
        Server server,
        Scheduler scheduler
    ) {
        this.punishmentRepository = punishmentRepository;
        this.punishmentHistoryService = punishmentHistoryService;
        this.punishmentSettings = punishmentSettings;
        this.templateRenderer = templateRenderer;
        this.noticeService = noticeService;
        this.server = server;
        this.scheduler = scheduler;

        this.loadActivePunishments();
    }

    @Override
    public Punishment ban(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage) {
        this.assertNotPrimaryThread();
        this.requireNonEmpty(kickMessage);

        Punishment punishment = Punishment.builder()
            .target(target)
            .operator(operator)
            .type(PunishmentType.BAN)
            .reason(reason)
            .expiresAt(expiresAt)
            .build();

        Punishment saved = this.persistAndCache(punishment, this.activeBans, HistoryAction.BAN);
        this.kickIfOnline(target.uuid(), kickMessage);

        return saved;
    }

    @Override
    public void unban(PunishmentTarget target, PunishmentTarget operator) {
        this.assertNotPrimaryThread();
        this.deactivateAndUncache(target, operator, this.activeBans, HistoryAction.UNBAN);
    }

    @Override
    public void kick(PunishmentTarget target, PunishmentTarget operator, String reason, List<Component> kickMessage, boolean massKick) {
        this.assertNotPrimaryThread();
        this.requireNonEmpty(kickMessage);

        Punishment punishment = Punishment.builder()
            .target(target)
            .operator(operator)
            .type(PunishmentType.KICK)
            .reason(reason)
            .build();

        this.kickIfOnline(target.uuid(), kickMessage);

        HistoryAction action = massKick ? HistoryAction.KICK_ALL : HistoryAction.KICK;

        this.punishmentRepository.save(punishment).join();
        this.recordHistory(punishment, action);
    }

    @Override
    public Punishment mute(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        this.assertNotPrimaryThread();

        Punishment punishment = Punishment.builder()
            .target(target)
            .operator(operator)
            .type(PunishmentType.MUTE)
            .reason(reason)
            .expiresAt(expiresAt)
            .build();

        return this.persistAndCache(punishment, this.activeMutes, HistoryAction.MUTE);
    }

    @Override
    public void unmute(PunishmentTarget target, PunishmentTarget operator) {
        this.assertNotPrimaryThread();
        this.deactivateAndUncache(target, operator, this.activeMutes, HistoryAction.UNMUTE);
    }

    @Override
    public Punishment warn(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        this.assertNotPrimaryThread();

        Punishment punishment = Punishment.builder()
            .target(target)
            .operator(operator)
            .type(PunishmentType.WARN)
            .reason(reason)
            .expiresAt(expiresAt)
            .build();

        this.punishmentRepository.save(punishment).join();
        this.recordHistory(punishment, HistoryAction.WARN);

        int warnCount = this.punishmentRepository.countByTargetAndType(target.uuid(), PunishmentType.WARN, Instant.now()).join();

        this.applyEscalationIfConfigured(target, operator, warnCount);
        this.activeWarns.put(target.uuid(), punishment);

        return punishment;
    }

    private void applyEscalationIfConfigured(PunishmentTarget target, PunishmentTarget operator, int warnCount) {
        String raw = this.punishmentSettings.warnEscalations().get(warnCount);

        if (raw == null) {
            return;
        }

        WarnEscalation escalation = WarnEscalationParser.parse(raw);
        String autoReason = this.punishmentSettings.warnEscalationReason().replace("{COUNT}", String.valueOf(warnCount));
        Instant expiresAt = escalation.duration().map(duration -> Instant.now().plus(duration)).orElse(null);
        String expiresText = expiresAt == null ? this.punishmentSettings.permanentLabel() : DurationUtil.format(escalation.duration().orElseThrow(), true);

        switch (escalation.action()) {
            case KICK -> this.kick(target, operator, autoReason, this.renderKickScreen(target, operator, autoReason), false);
            case MUTE -> this.mute(target, operator, autoReason, expiresAt);
            case BAN -> this.ban(target, operator, autoReason, expiresAt, this.renderBanScreen(target, operator, autoReason, expiresText));
        }

        this.noticeService.create()
            .notice(translation -> translation.punishment().warnEscalationBroadcast())
            .placeholder("{PLAYER}", target.name())
            .placeholder("{ACTION}", escalation.action().name())
            .placeholder("{EXPIRES}", expiresText)
            .placeholder("{COUNT}", String.valueOf(warnCount))
            .all()
            .send();
    }

    private List<Component> renderKickScreen(PunishmentTarget target, PunishmentTarget operator, String reason) {
        return this.templateRenderer.render(
            this.punishmentSettings.kickScreen(),
            Map.of(
                "{PLAYER}", target.name(),
                "{OPERATOR}", operator.name(),
                "{REASON}", reason
            )
        );
    }

    private List<Component> renderBanScreen(PunishmentTarget target, PunishmentTarget operator, String reason, String expiresText) {
        return this.templateRenderer.render(
            this.punishmentSettings.banKickScreen(),
            Map.of(
                "{PLAYER}", target.name(),
                "{OPERATOR}", operator.name(),
                "{REASON}", reason,
                "{EXPIRES}", expiresText
            )
        );
    }

    @Override
    public boolean isBanned(UUID targetUuid) {
        return this.getActiveBan(targetUuid).isPresent();
    }

    @Override
    public boolean isMuted(UUID targetUuid) {
        return this.getActiveMute(targetUuid).isPresent();
    }

    @Override
    public Optional<Punishment> getActiveBan(UUID targetUuid) {
        return this.getActiveFromCache(this.activeBans, targetUuid);
    }

    @Override
    public Optional<Punishment> getActiveMute(UUID targetUuid) {
        return this.getActiveFromCache(this.activeMutes, targetUuid);
    }

    @Override
    public List<Punishment> findActive(UUID targetUuid) {
        this.assertNotPrimaryThread();
        return this.punishmentRepository.findActive(targetUuid).join();
    }

    private Optional<Punishment> getActiveFromCache(Map<UUID, Punishment> cache, UUID targetUuid) {
        Punishment punishment = cache.get(targetUuid);

        if (punishment == null) {
            return Optional.empty();
        }

        if (!punishment.isActive()) {
            cache.remove(targetUuid);
            return Optional.empty();
        }

        return Optional.of(punishment);
    }

    private Punishment persistAndCache(Punishment punishment, Map<UUID, Punishment> cache, HistoryAction action) {
        this.punishmentRepository.save(punishment).join();
        this.recordHistory(punishment, action);

        cache.put(punishment.target().uuid(), punishment);
        return punishment;
    }

    private void deactivateAndUncache(PunishmentTarget target, PunishmentTarget operator, Map<UUID, Punishment> cache, HistoryAction action) {
        Punishment cached = cache.remove(target.uuid());

        if (cached == null) {
            return;
        }

        PunishmentHistoryEntry entry = new PunishmentHistoryEntry(
            UUID.randomUUID(),
            cached.id(),
            target,
            operator,
            action,
            "",
            Instant.now(),
            null
        );

        this.punishmentRepository.deactivate(cached.id()).join();
        this.punishmentHistoryService.record(entry);
    }

    private void recordHistory(Punishment punishment, HistoryAction action) {
        PunishmentHistoryEntry entry = new PunishmentHistoryEntry(
            UUID.randomUUID(),
            punishment.id(),
            punishment.target(),
            punishment.operator(),
            action,
            punishment.reason(),
            punishment.createdAt(),
            punishment.expiresAtOptional().orElse(null)
        );

        this.punishmentHistoryService.record(entry);
    }

    private void kickIfOnline(UUID targetUuid, List<Component> message) {
        this.scheduler.run(() -> {
            Player player = this.server.getPlayer(targetUuid);

            if (player != null) {
                player.kick(Component.join(JoinConfiguration.newlines(), message));
            }
        });
    }

    private void requireNonEmpty(List<Component> kickMessage) {
        if (kickMessage.isEmpty()) {
            throw new IllegalArgumentException("kickMessage cannot be empty");
        }
    }

    private void assertNotPrimaryThread() {
        if (this.server.isPrimaryThread()) {
            throw new IllegalStateException("PunishmentService must not be called from the main thread");
        }
    }

    private void loadActivePunishments() {
        this.loadActiveInto(this.activeBans, PunishmentType.BAN);
        this.loadActiveInto(this.activeMutes, PunishmentType.MUTE);
        this.loadUnexpiredWarnsInto(this.activeWarns);
    }

    private void loadActiveInto(Map<UUID, Punishment> cache, PunishmentType type) {
        this.punishmentRepository.findAllActive(type).thenAccept(punishments -> {
            for (Punishment punishment : punishments) {
                if (punishment.isActive()) {
                    cache.put(punishment.target().uuid(), punishment);
                }
            }
        });
    }

    private void loadUnexpiredWarnsInto(Map<UUID, Punishment> cache) {
        this.punishmentRepository.findAllUnexpired(PunishmentType.WARN, Instant.now()).thenAccept(punishments -> {
            for (Punishment punishment : punishments) {
                cache.put(punishment.target().uuid(), punishment);
            }
        });
    }

    @Override
    public List<Punishment> activeBans() {
        return List.copyOf(this.activeBans.values());
    }

    @Override
    public List<Punishment> activeMutes() {
        return List.copyOf(this.activeMutes.values());
    }

    @Override
    public List<Punishment> activeWarns() {
        return this.activeWarns.values().stream()
            .filter(Punishment::isActive)
            .toList();
    }
}
