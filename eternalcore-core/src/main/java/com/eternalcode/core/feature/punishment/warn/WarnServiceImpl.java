package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.punishment.ActivePunishmentCache;
import com.eternalcode.core.feature.punishment.PunishmentGuard;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.database.WarnRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.time.Instant;
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
    WarnServiceImpl(WarnRepository warnRepository, WarnEscalationApplier escalationApplier, PunishmentGuard punishmentGuard) {
        this.warnRepository = warnRepository;
        this.escalationApplier = escalationApplier;
        this.punishmentGuard = punishmentGuard;

        this.loadActiveWarns();
    }

    @Override
    public Warn warn(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        Warn warn = Warn.issue(target, operator, reason, expiresAt);

        this.warnRepository.save(warn).join();

        int activeWarnCount = this.warnRepository.findActive(target.uuid()).join().size();

        this.escalationApplier.applyIfConfigured(target, operator, activeWarnCount);
        this.latestActiveWarns.put(target.uuid(), warn);

        return warn;
    }

    @Override
    public List<Warn> activeWarns() {
        return this.latestActiveWarns.active();
    }

    private void loadActiveWarns() {
        this.warnRepository.findAllActive()
            .thenAccept(warns -> warns.forEach(warn -> this.latestActiveWarns.put(warn.target().uuid(), warn)))
            .exceptionally(FutureHandler::handleException);
    }
}
