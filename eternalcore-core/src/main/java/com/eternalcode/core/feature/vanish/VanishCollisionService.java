package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;

@Service
class VanishCollisionService {

    private final Map<UUID, Boolean> previousCollidableStates = new ConcurrentHashMap<>();

    @Inject
    VanishCollisionService() {
    }

    void disableCollision(Player player) {
        this.previousCollidableStates.putIfAbsent(player.getUniqueId(), player.isCollidable());
        player.setCollidable(false);
    }

    void restoreCollision(Player player) {
        Boolean previousCollidableState = this.previousCollidableStates.remove(player.getUniqueId());

        if (previousCollidableState == null) {
            return;
        }

        player.setCollidable(previousCollidableState);
    }
}
