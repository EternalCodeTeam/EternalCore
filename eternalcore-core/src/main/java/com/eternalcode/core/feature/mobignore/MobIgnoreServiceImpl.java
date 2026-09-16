package com.eternalcode.core.feature.mobignore;

import com.eternalcode.core.injector.annotations.component.Service;
import java.util.HashSet;
import java.util.UUID;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

@Service
public class MobIgnoreServiceImpl implements MobIgnoreService {

    private final HashSet<UUID> ignoredPlayers = new HashSet<>();

    @Override
    public void removeTracking(Player player) {
        this.ignoredPlayers.add(player.getUniqueId());

        this.enableNoTarget(player);
    }

    @Override
    public boolean isIgnored(UUID uniqueId) {
        return this.ignoredPlayers.contains(uniqueId);
    }

    @Override
    public void startTracking(UUID uniqueId) {
        this.ignoredPlayers.remove(uniqueId);
    }

    private void enableNoTarget(Player player) {
        for (Entity entity : player.getNearbyEntities(30, 30, 30)) {
            if (entity instanceof Mob mob) {

                if (mob.getTarget() != null && mob.getTarget().equals(player)) {
                    mob.setTarget(null);
                }
            }
        }
    }
}
