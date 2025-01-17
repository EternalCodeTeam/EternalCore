package com.eternalcode.core.feature.catboy;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.catboy.event.CatboyChangeTypeEvent;
import com.eternalcode.core.feature.catboy.event.CatboySwitchEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@Service
class CatboyServiceImpl implements CatboyService {

    private static final float DEFAULT_WALK_SPEED = 0.2F;
    private final Map<UUID, Catboy> catboys = new HashMap<>();
    private final EventCaller eventCaller;

    private final CatBoyPersistentDataKey catBoyPersistentDataKey;

    @Inject
    CatboyServiceImpl(EventCaller eventCaller, CatBoyPersistentDataKey catBoyPersistentDataKey) {
        this.eventCaller = eventCaller;
        this.catBoyPersistentDataKey = catBoyPersistentDataKey;
    }

    @Override
    public void markAsCatboy(Player player, Cat.Type type) {
        Catboy catboy = new Catboy(player.getUniqueId(), type);
        this.catboys.put(player.getUniqueId(), catboy);

        Cat entity = (Cat) player.getWorld().spawnEntity(player.getLocation(), EntityType.CAT);
        entity.setInvulnerable(true);
        entity.setOwner(player);
        entity.setAI(false);
        entity.setCatType(type);

        player.addPassenger(entity);
        player.setWalkSpeed(0.4F);

        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        persistentDataContainer.set(this.catBoyPersistentDataKey.getCatboyDataKey(), PersistentDataType.STRING, "catboy");

        this.eventCaller.callEvent(new CatboySwitchEvent(player, true));
    }

    @Override
    public void unmarkAsCatboy(Player player) {
        this.catboys.remove(player.getUniqueId());
        player.getPassengers().forEach(entity -> entity.remove());
        player.getPassengers().clear();
        player.setWalkSpeed(DEFAULT_WALK_SPEED);

        this.eventCaller.callEvent(new CatboySwitchEvent(player, false));
    }

    @Override
    public void changeCatboyType(Player target, Cat.Type type) {
        Catboy catboy = this.catboys.get(target.getUniqueId());

        if (catboy == null) {
            return;
        }

        CatboyChangeTypeEvent event = new CatboyChangeTypeEvent(target, catboy.selectedType(), type);

        if (event.isCancelled()) {
            return;
        }

        this.catboys.put(target.getUniqueId(), new Catboy(target.getUniqueId(), type));

        target.getPassengers().forEach(entity -> {
            if (entity instanceof Cat cat) {
                cat.setCatType(type);
            }
        });
    }

    @Override
    public boolean isCatboy(UUID uuid) {
        return this.catboys.containsKey(uuid);
    }

    @Override
    public Optional<Catboy> getCatboy(UUID uuid) {
        return Optional.ofNullable(this.catboys.get(uuid));
    }

    @Override
    public Collection<Catboy> getCatboys() {
        return Collections.unmodifiableCollection(this.catboys.values());
    }
}
