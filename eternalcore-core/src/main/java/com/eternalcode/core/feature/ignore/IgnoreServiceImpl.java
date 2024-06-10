package com.eternalcode.core.feature.ignore;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.ignore.event.IgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

// TODO: IgnoreService interface and IgnoreRepository. IgnoreService calls IgnoreRepository methods. IgnoreService manages events, whereas IgnoreRepository manages the database.

public class IgnoreServiceImpl implements IgnoreService {

    private final IgnoreRepository ignoreRepository;
    private final Server server;
    private final EventCaller caller;

    public IgnoreServiceImpl(IgnoreRepository ignoreRepository, Server server, EventCaller caller) {
        this.ignoreRepository = ignoreRepository;
        this.server = server;
        this.caller = caller;
    }

    @Override
    public CompletableFuture<Boolean> isIgnored(UUID by, UUID target) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> ignore(UUID by, UUID target) {
        Player senderPlayer = this.server.getPlayer(by);
        Player targetPlayer = this.server.getPlayer(target);
        IgnoreEvent event = this.caller.callEvent(new IgnoreEvent(senderPlayer, targetPlayer));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(false);
        }

        this.ignoreRepository.ignore(by, target);
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public CompletableFuture<Boolean> ignoreAll(UUID by) {
        IgnoreAllEvent event = this.caller.callEvent(new IgnoreAllEvent(this.server.getPlayer(by)));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(false);
        }
        this.ignoreRepository.ignoreAll(by);
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public CompletableFuture<Boolean> unIgnore(UUID by, UUID target) {
        Player senderPlayer = this.server.getPlayer(by);
        Player targetPlayer = this.server.getPlayer(target);

        UnIgnoreEvent event = this.caller.callEvent(new UnIgnoreEvent(senderPlayer, targetPlayer));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(false);
        }
        this.ignoreRepository.unIgnore(by, target);
        return CompletableFuture.completedFuture(true);
    }

    @Override
    public CompletableFuture<Boolean> unIgnoreAll(UUID by) {
        UnIgnoreAllEvent event = this.caller.callEvent(new UnIgnoreAllEvent(this.server.getPlayer(by)));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(false);
        }
        this.ignoreRepository.unIgnoreAll(by);
        return CompletableFuture.completedFuture(true);
    }

}
