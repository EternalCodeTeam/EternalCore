package com.eternalcode.core.feature.ignore;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.ignore.event.IgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
class IgnoreServiceImpl implements IgnoreService {

    private final IgnoreRepository ignoreRepository;
    private final Server server;
    private final EventCaller caller;

    @Inject
    IgnoreServiceImpl(IgnoreRepository ignoreRepository, Server server, EventCaller caller) {
        this.ignoreRepository = ignoreRepository;
        this.server = server;
        this.caller = caller;
    }

    @Override
    public CompletableFuture<Boolean> isIgnored(UUID by, UUID target) {
        return this.ignoreRepository.isIgnored(by, target);
    }

    @Override
    public CompletableFuture<Boolean> ignore(UUID by, UUID target) {
        Player senderPlayer = this.server.getPlayer(by);
        Player targetPlayer = this.server.getPlayer(target);

        if (senderPlayer == null) {
            return CompletableFuture.completedFuture(false);
        }

        IgnoreEvent event = this.caller.callEvent(new IgnoreEvent(senderPlayer, targetPlayer));

        if (event.isCancelled() || targetPlayer == null) {
            return CompletableFuture.completedFuture(false);
        }

        return this.ignoreRepository.ignore(by, target).thenApply(unused -> true);
    }

    @Override
    public CompletableFuture<Boolean> ignoreAll(UUID by) {
        Player player = this.server.getPlayer(by);

        if (player == null) {
            return CompletableFuture.completedFuture(false);
        }

        IgnoreAllEvent event = this.caller.callEvent(new IgnoreAllEvent(player));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(false);
        }
        return this.ignoreRepository.ignoreAll(by).thenApply(unused -> true);
    }

    @Override
    public CompletableFuture<Boolean> unIgnore(UUID by, UUID target) {
        Player senderPlayer = this.server.getPlayer(by);
        Player targetPlayer = this.server.getPlayer(target);

        if (senderPlayer == null) {
            return CompletableFuture.completedFuture(false);
        }

        UnIgnoreEvent event = this.caller.callEvent(new UnIgnoreEvent(senderPlayer, targetPlayer));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(false);
        }
        return this.ignoreRepository.unIgnore(by, target).thenApply(unused -> true);
    }

    @Override
    public CompletableFuture<Boolean> unIgnoreAll(UUID by) {
        Player player = this.server.getPlayer(by);

        if (player == null) {
            return CompletableFuture.completedFuture(false);
        }

        UnIgnoreAllEvent event = this.caller.callEvent(new UnIgnoreAllEvent(player));

        if (event.isCancelled()) {
            return CompletableFuture.completedFuture(false);
        }
        return this.ignoreRepository.unIgnoreAll(by).thenApply(unused -> true);
    }

}
