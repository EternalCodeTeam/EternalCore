package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.entity.Player;

import java.util.UUID;

@Service
public class VanishServiceImpl implements VanishService {

    private final VanishInvisibleService vanishInvisibleService;
    private final VanishMetaDataService vanishMetaDataService;
    private final EventCaller eventCaller;

    @Inject
    public VanishServiceImpl(VanishInvisibleService vanishInvisibleService, VanishMetaDataService vanishMetaDataService, EventCaller eventCaller) {
        this.vanishInvisibleService = vanishInvisibleService;
        this.vanishMetaDataService = vanishMetaDataService;
        this.eventCaller = eventCaller;
    }

    @Override
    public void enableVanish(Player player) {
        this.vanishInvisibleService.hidePlayer(player);
        this.vanishMetaDataService.addMetadata(player);

        this.eventCaller.callEvent(new EnableVanishEvent(player));
    }

    @Override
    public void disableVanish(Player player) {
        this.vanishInvisibleService.showPlayer(player);
        this.vanishMetaDataService.removeMetadata(player);

        this.eventCaller.callEvent(new DisableVanishEvent(player));
    }

    @Override
    public boolean isVanished(Player player) {
        return this.vanishMetaDataService.hasMetadata(player);
    }

    @Override
    public boolean isVanished(UUID uniqueId) {
        return this.vanishMetaDataService.hasMetadata(uniqueId);
    }

    @Override
    public void hideVanishedPlayersFrom(Player player) {
        this.vanishInvisibleService.hideVanishedPlayersFrom(player);
    }
}
