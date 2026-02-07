package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
class VanishServiceImpl implements VanishService {

    private final VanishInvisibleService vanishInvisibleService;
    private final VanishMetaDataService vanishMetaDataService;
    private final EventCaller eventCaller;

    @Inject
    VanishServiceImpl(
        VanishInvisibleService vanishInvisibleService,
        VanishMetaDataService vanishMetaDataService,
        EventCaller eventCaller
    ) {
        this.vanishInvisibleService = vanishInvisibleService;
        this.vanishMetaDataService = vanishMetaDataService;
        this.eventCaller = eventCaller;
    }

    @Override
    public void enableVanish(@NotNull Player player) {
        EnableVanishEvent event = this.eventCaller.callEvent(new EnableVanishEvent(player));

        if (event.isCancelled()) {
            return;
        }

        this.vanishInvisibleService.hidePlayer(player);
        this.vanishMetaDataService.addMetadata(player);
    }

    @Override
    public void disableVanish(@NotNull Player player) {
        DisableVanishEvent event = this.eventCaller.callEvent(new DisableVanishEvent(player));

        if (event.isCancelled()) {
            return;
        }

        this.vanishInvisibleService.showPlayer(player);
        this.vanishMetaDataService.removeMetadata(player);
    }

    @Override
    public boolean isVanished(@NotNull Player player) {
        return this.vanishInvisibleService.getVanishedPlayers().contains(player.getUniqueId());
    }

    @Override
    public boolean isVanished(@NotNull UUID uniqueId) {
        return this.vanishInvisibleService.getVanishedPlayers().contains(uniqueId);
    }

    @Override
    public void hideVanishedPlayersFrom(@NotNull Player player) {
        this.vanishInvisibleService.hideVanishedPlayersFrom(player);
    }

    @Override
    public Set<@Nullable UUID> getVanishedPlayers() {
        return this.vanishInvisibleService.getVanishedPlayers();
    }

    @Override
    public Set<@Nullable String> getVanishedPlayerNames() {
        return this.vanishInvisibleService.getVanishedPlayerNames();
    }
}
