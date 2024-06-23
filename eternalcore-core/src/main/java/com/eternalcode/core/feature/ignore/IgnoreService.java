package com.eternalcode.core.feature.ignore;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.concurrent.CompletableFuture;

import java.util.UUID;

@Service
public class IgnoreService {

    private final IgnoreRepository repository;

    @Inject
    IgnoreService(IgnoreRepository repository) {
        this.repository = repository;
    }

    public CompletableFuture<Boolean> isIgnored(UUID by, UUID target) {
        return this.repository.isIgnored(by, target);
    }

}
