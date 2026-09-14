package com.eternalcode.core.feature.notarget;

import com.eternalcode.core.injector.annotations.component.Service;
import java.util.HashSet;
import java.util.UUID;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

@Service
public class MobTargetServiceImpl implements MobTargetService {

    private final HashSet<UUID> mobTargetMap = new HashSet<>();

    @Override
    public void removeTracking(Player player) {
        this.mobTargetMap.add(player.getUniqueId());

        this.enableNoTarget(player);
    }

    @Override
    public boolean doMobsIgnore(UUID uniqueId) {
        return this.mobTargetMap.contains(uniqueId);
    }

    @Override
    public void startTracking(UUID uniqueId) {
        this.mobTargetMap.remove(uniqueId);
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
