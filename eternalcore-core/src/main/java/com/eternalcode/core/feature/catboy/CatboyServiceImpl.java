package com.eternalcode.core.feature.catboy;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.catboy.event.CatboySwitchEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
class CatboyServiceImpl implements CatboyService {

    private final Set<UUID> catboys = new HashSet<>();
    private final EventCaller eventCaller;

    @Inject
    CatboyServiceImpl(EventCaller eventCaller) {
        this.eventCaller = eventCaller;
    }

    @Override
    public void markAsCatboy(Player player) {
        this.catboys.add(player.getUniqueId());
        player.addPassenger(player.getWorld().spawnEntity(player.getLocation(), EntityType.CAT));
        player.setWalkSpeed(0.4F);

        this.eventCaller.callEvent(new CatboySwitchEvent(player, true));
    }

    @Override
    public void unmarkAsCatboy(Player player) {
        this.catboys.remove(player.getUniqueId());
        player.getPassengers().forEach(entity -> entity.remove());
        player.getPassengers().clear();
        player.setWalkSpeed(0.2F);

        this.eventCaller.callEvent(new CatboySwitchEvent(player, false));
    }

    @Override
    public boolean isCatboy(UUID uuid) {
        return this.catboys.contains(uuid);
    }

    @Override
    public Set<UUID> getCatboys() {
        return Collections.unmodifiableSet(this.catboys);
    }

}
