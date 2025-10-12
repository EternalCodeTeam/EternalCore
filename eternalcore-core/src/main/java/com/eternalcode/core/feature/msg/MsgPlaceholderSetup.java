package com.eternalcode.core.feature.msg;

import com.eternalcode.core.feature.msg.toggle.MsgState;
import com.eternalcode.core.feature.msg.toggle.MsgToggleService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Controller
class MsgPlaceholderSetup {

    private static final Duration CACHE_DURATION = Duration.ofSeconds(5);

    private final MsgService msgService;
    private final MsgToggleService msgToggleService;
    private final TranslationManager translationManager;
    private final ConcurrentHashMap<UUID, CachedState> stateCache = new ConcurrentHashMap<>();

    @Inject
    MsgPlaceholderSetup(MsgService msgService, MsgToggleService msgToggleService, TranslationManager translationManager) {
        this.msgService = msgService;
        this.msgToggleService = msgToggleService;
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry) {
        Translation translation = this.translationManager.getMessages();

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "socialspy_status",
            player -> {
                UUID uuid = player.getUniqueId();
                return String.valueOf(this.msgService.isSpy(uuid));
            }
        ));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "socialspy_status_formatted",
            player -> {
                UUID uuid = player.getUniqueId();
                return this.msgService.isSpy(uuid)
                    ? translation.msg().placeholders().socialSpyEnabled()
                    : translation.msg().placeholders().socialSpyDisabled();
            }
        ));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "msg_status",
            player -> {
                UUID uuid = player.getUniqueId();
                MsgState state = this.getCachedOrLoadState(uuid);

                if (state == null) {
                    return translation.msg().placeholders().loading();
                }

                return state.name().toLowerCase();
            }
        ));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "msg_status_formatted",
            player -> {
                UUID uuid = player.getUniqueId();
                MsgState state = this.getCachedOrLoadState(uuid);

                if (state == null) {
                    return translation.msg().placeholders().loading();
                }

                return state == MsgState.ENABLED
                    ? translation.msg().placeholders().msgEnabled()
                    : translation.msg().placeholders().msgDisabled();
            }
        ));
    }

    private MsgState getCachedOrLoadState(UUID uuid) {
        CachedState cached = this.stateCache.get(uuid);

        if (cached != null && !cached.isExpired()) {
            return cached.state();
        }

        CompletableFuture<MsgState> future = this.msgToggleService.getState(uuid);

        if (future.isDone() && !future.isCompletedExceptionally()) {
            MsgState state = future.join();
            this.stateCache.put(uuid, new CachedState(state, Instant.now()));
            return state;
        }

        future.thenAccept(state ->
            this.stateCache.put(uuid, new CachedState(state, Instant.now()))
        );

        return null;
    }

    private record CachedState(MsgState state, Instant timestamp) {
        boolean isExpired() {
            return Duration.between(this.timestamp, Instant.now()).compareTo(CACHE_DURATION) > 0;
        }
    }
}
