package com.eternalcode.core.feature.ignore;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.ignore.event.IgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
class IgnoreServiceImpl implements IgnoreService {

    private final IgnoreRepository ignoreRepository;
    private final EventCaller caller;

    @Inject
    IgnoreServiceImpl(IgnoreRepository ignoreRepository, EventCaller caller) {
        this.ignoreRepository = ignoreRepository;
        this.caller = caller;
    }

    @Override
    public CompletableFuture<Boolean> isIgnored(UUID requester, UUID target) {
        return this.ignoreRepository.isIgnored(requester, target);
    }

    @Override
    public void ignore(UUID requester, UUID target) {
        IgnoreEvent event = this.caller.callEvent(new IgnoreEvent(requester, target));

        if (event.isCancelled()) {
            return;
        }

        this.ignoreRepository.ignore(requester, target);
    }

    @Override
    public void ignoreAll(UUID requester) {
        IgnoreAllEvent event = this.caller.callEvent(new IgnoreAllEvent(requester));

        if (event.isCancelled()) {
            return;
        }

        this.ignoreRepository.ignoreAll(requester).thenApply(unused -> true);
    }

    @Override
    public void unIgnore(UUID requester, UUID target) {
        UnIgnoreEvent event = this.caller.callEvent(new UnIgnoreEvent(requester, target));

        if (event.isCancelled()) {
            return;
        }

        this.ignoreRepository.unIgnore(requester, target).thenApply(unused -> true);
    }

    @Override
    public void unIgnoreAll(UUID requester) {
        UnIgnoreAllEvent event = this.caller.callEvent(new UnIgnoreAllEvent(requester));

        if (event.isCancelled()) {
            return;
        }

        this.ignoreRepository.unIgnoreAll(requester).thenApply(unused -> true);
    }
}
