package com.eternalcode.core.feature.punishment.mute;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.punishment.ActivePunishmentCache;
import com.eternalcode.core.feature.punishment.PunishmentGuard;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.database.MuteRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class MuteServiceImpl implements MuteService {

    private static final String SERVICE_NAME = "MuteService";

    private final ActivePunishmentCache<UUID, Mute> activeMutes = new ActivePunishmentCache<>();

    private final MuteRepository muteRepository;
    private final PunishmentGuard punishmentGuard;

    @Inject
    MuteServiceImpl(MuteRepository muteRepository, PunishmentGuard punishmentGuard) {
        this.muteRepository = muteRepository;
        this.punishmentGuard = punishmentGuard;

        this.loadActiveMutes();
    }

    @Override
    public Mute mute(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        Mute mute = Mute.issue(target, operator, reason, expiresAt);

        this.muteRepository.save(mute).join();
        this.activeMutes.put(target.uuid(), mute);

        return mute;
    }

    @Override
    public void unmute(PunishmentTarget target, PunishmentTarget operator) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        this.activeMutes.remove(target.uuid())
            .ifPresent(mute -> this.muteRepository.revoke(mute.id(), operator, Instant.now()).join());
    }

    @Override
    public boolean isMuted(UUID targetUuid) {
        return this.getActiveMute(targetUuid).isPresent();
    }

    @Override
    public Optional<Mute> getActiveMute(UUID targetUuid) {
        return this.activeMutes.get(targetUuid);
    }

    @Override
    public List<Mute> activeMutes() {
        return this.activeMutes.active();
    }

    private void loadActiveMutes() {
        this.muteRepository.findAllActive()
            .thenAccept(mutes -> mutes.forEach(mute -> this.activeMutes.put(mute.target().uuid(), mute)))
            .exceptionally(FutureHandler::handleException);
    }
}
