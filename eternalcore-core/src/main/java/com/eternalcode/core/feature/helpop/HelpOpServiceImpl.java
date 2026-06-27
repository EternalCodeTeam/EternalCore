package com.eternalcode.core.feature.helpop;

import com.eternalcode.core.injector.annotations.component.Service;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.time.Duration;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

@Service
class HelpOpServiceImpl implements HelpOpService {

    private final Cache<UUID, Boolean> helpOpSenders = CacheBuilder.newBuilder()
        .expireAfterWrite(Duration.ofHours(1))
        .build();

    @Override
    public void markSender(@NonNull UUID playerUuid) {
        this.helpOpSenders.put(playerUuid, true);
    }

    @Override
    public boolean hasSentHelpOp(@NonNull UUID playerUuid) {
        return this.helpOpSenders.getIfPresent(playerUuid) != null;
    }
}
