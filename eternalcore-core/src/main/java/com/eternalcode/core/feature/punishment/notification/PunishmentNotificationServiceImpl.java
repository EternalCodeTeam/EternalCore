package com.eternalcode.core.feature.punishment.notification;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
class PunishmentNotificationServiceImpl implements PunishmentNotificationService {

    private static final String HIDDEN_KEY = "punishment_notifications_hidden";
    private static final byte HIDDEN = 1;

    private final Server server;
    private final NamespacedKey hiddenKey;
    private final Set<UUID> hiddenFor = ConcurrentHashMap.newKeySet();

    @Inject
    PunishmentNotificationServiceImpl(Plugin plugin, Server server) {
        this.server = server;
        this.hiddenKey = new NamespacedKey(plugin, HIDDEN_KEY);
    }

    @Override
    public boolean isHidden(UUID playerUniqueId) {
        return this.hiddenFor.contains(playerUniqueId);
    }

    @Override
    public void setHidden(UUID playerUniqueId, boolean hidden) {
        Player player = this.server.getPlayer(playerUniqueId);

        if (player == null) {
            throw new IllegalArgumentException("Player " + playerUniqueId + " must be online to change punishment notifications");
        }

        PersistentDataContainer container = player.getPersistentDataContainer();

        if (hidden) {
            container.set(this.hiddenKey, PersistentDataType.BYTE, HIDDEN);
            this.hiddenFor.add(playerUniqueId);
            return;
        }

        container.remove(this.hiddenKey);
        this.hiddenFor.remove(playerUniqueId);
    }

    void load(Player player) {
        if (player.getPersistentDataContainer().has(this.hiddenKey, PersistentDataType.BYTE)) {
            this.hiddenFor.add(player.getUniqueId());
        }
    }

    void unload(UUID playerUniqueId) {
        this.hiddenFor.remove(playerUniqueId);
    }
}
