package com.eternalcode.core.feature.mobignore;

import com.eternalcode.core.injector.annotations.component.Service;
import java.util.HashSet;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

@Service
public class MobIgnoreServiceImpl implements MobIgnoreService {

    private final HashSet<UUID> ignoredPlayers = new HashSet<>();
    private final Server server;

    public MobIgnoreServiceImpl(Server server) {
        this.server = server;
    }

    @Override
    public void ignore(UUID uniqueId) {
        this.ignoredPlayers.add(uniqueId);

        Player player = this.server.getPlayer(uniqueId);

        if (player == null) {
            return;
        }

        this.enableNoTarget(player);
    }

    @Override
    public void unignore(UUID uniqueId) {
        this.ignoredPlayers.remove(uniqueId);
    }

    @Override
    public boolean isIgnored(UUID uniqueId) {
        return this.ignoredPlayers.contains(uniqueId);
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
