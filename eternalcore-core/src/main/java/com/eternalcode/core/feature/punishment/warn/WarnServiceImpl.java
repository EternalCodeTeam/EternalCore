package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.ActivePunishmentCache;
import com.eternalcode.core.feature.punishment.PunishmentGuard;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
class WarnServiceImpl implements WarnService {

    private static final String SERVICE_NAME = "WarnService";

    private final ActivePunishmentCache<UUID, Warn> latestActiveWarns = new ActivePunishmentCache<>();

    private final WarnRepository warnRepository;
    private final WarnEscalationApplier escalationApplier;
    private final PunishmentGuard punishmentGuard;

    @Inject
    WarnServiceImpl(
        WarnRepository warnRepository,
        WarnEscalationApplier escalationApplier,
        PunishmentGuard punishmentGuard,
        Scheduler scheduler
    ) {
        this.warnRepository = warnRepository;
        this.escalationApplier = escalationApplier;
        this.punishmentGuard = punishmentGuard;

        scheduler.runAsync(this::loadActiveWarns);
    }

    @Override
    public Warn warn(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        Warn warn = WarnImpl.issue(target, operator, reason, expiresAt);

        this.warnRepository.save(warn);

        int activeWarnCount = this.warnRepository.findActive(target.uuid()).size();

        this.escalationApplier.applyIfConfigured(target, operator, activeWarnCount);
        this.latestActiveWarns.put(target.uuid(), warn);

        return warn;
    }

    @Override
    public boolean revokeWarn(UUID warnId, PunishmentActor operator) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        boolean revoked = this.warnRepository.revoke(warnId, operator, Instant.now());

        if (revoked) {
            this.refreshLatestWarnAfterRevoke(warnId);
        }

        return revoked;
    }

    @Override
    public List<Warn> activeWarns() {
        return this.latestActiveWarns.active();
    }

    private void refreshLatestWarnAfterRevoke(UUID revokedWarnId) {
        this.latestActiveWarns.active().stream()
            .filter(warn -> warn.id().equals(revokedWarnId))
            .findFirst()
            .ifPresent(revokedWarn -> {
                UUID targetUuid = revokedWarn.target().uuid();

                this.latestActiveWarns.remove(targetUuid);
                this.warnRepository.findActive(targetUuid).stream()
                    .max(Comparator.comparing(Warn::createdAt))
                    .ifPresent(latestWarn -> this.latestActiveWarns.put(targetUuid, latestWarn));
            });
    }

    private void loadActiveWarns() {
        this.warnRepository.findAllActive()
            .forEach(warn -> this.latestActiveWarns.put(warn.target().uuid(), warn));
    }
}
