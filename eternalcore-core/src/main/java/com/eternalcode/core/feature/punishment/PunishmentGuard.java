package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import net.kyori.adventure.text.Component;

import org.bukkit.Server;

import java.util.List;

/**
 * Fail-fast preconditions shared by punishment services.
 */
@Service
public class PunishmentGuard {

    private final Server server;

    @Inject
    PunishmentGuard(Server server) {
        this.server = server;
    }

    public void assertAsync(String serviceName) {
        if (this.server.isPrimaryThread()) {
            throw new IllegalStateException(serviceName + " must not be called from the main thread");
        }
    }

    public void requireKickMessage(List<Component> kickMessage) {
        if (kickMessage.isEmpty()) {
            throw new IllegalArgumentException("kickMessage cannot be empty");
        }
    }
}
