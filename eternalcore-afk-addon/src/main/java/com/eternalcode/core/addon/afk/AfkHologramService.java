package com.eternalcode.core.addon.afk;

import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.core.addon.afk.config.PluginConfiguration;
import com.eternalcode.core.feature.afk.Afk;
import com.eternalcode.core.feature.afk.AfkService;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.holoeasy.HoloEasy;
import org.holoeasy.config.HologramKey;
import org.holoeasy.hologram.Hologram;
import org.holoeasy.pool.IHologramPool;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;
import static org.holoeasy.builder.HologramBuilder.hologram;
import static org.holoeasy.builder.HologramBuilder.item;
import static org.holoeasy.builder.HologramBuilder.textline;

public class AfkHologramService {

    private static final String HOLOGRAM_NAME_PREFIX = "afk#%s,%s,%s,%s";
    private static final int HOLOGRAM_VIEW_DISTANCE = 0x32;

    private final Map<UUID, HologramKey> holograms = new HashMap<>();
    private final PluginConfiguration configuration;
    private final MiniMessage miniMessage;
    private final AfkService afkService;
    private final IHologramPool pool;
    private final Plugin plugin;
    private final Server server;

    public AfkHologramService(PluginConfiguration configuration, MiniMessage miniMessage, AfkService afkService, Plugin plugin) {
        this.configuration = configuration;
        this.miniMessage = miniMessage;
        this.afkService = afkService;
        this.plugin = plugin;
        this.server = plugin.getServer();

        this.pool = HoloEasy.startPool(plugin, HOLOGRAM_VIEW_DISTANCE);
    }

    public void createHologram(Player player) {
        if (!this.afkService.isAfk(player.getUniqueId())) {
            return;
        }
        Afk afk = this.afkService.getAfk(player.getUniqueId());

        Duration afkSince = Duration.between(afk.getStart(), Instant.now());
        Location location = player.getLocation().clone();

        HologramKey key = new HologramKey(this.plugin, fetchName(location), this.pool);
        Hologram hologram = hologram(key, this.fetchLocation(player), () -> {
            if (this.configuration.hologramItemEnabled) {
                item(new ItemStack(this.configuration.hologramItem));
            }
            this.configuration.hologramEntries.stream()
                .map(entry -> entry.replace("{TIME}", DurationParser.TIME_UNITS.format(afkSince)))
                .forEach(entry -> textline(legacySection().serialize(this.miniMessage.deserialize(entry))));
        });

        this.holograms.put(player.getUniqueId(), key);

        this.showForAll(hologram);
    }

    public void deleteHologram(UUID uuid) {
        HologramKey hologramKey = this.holograms.remove(uuid);

        this.pool.remove(hologramKey);
    }

    public void updateHologram(Player player) {
        this.deleteHologram(player.getUniqueId());
        this.createHologram(player);
    }

    void showForAll(Hologram hologram) {
        this.server.getOnlinePlayers().forEach(hologram::show);
    }

    String fetchName(Location location) {
        return String.format(HOLOGRAM_NAME_PREFIX, location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

    Location fetchLocation(Player player) {
        return player.getLocation().add(0, 2, 0).clone();
    }

    public Set<UUID> getAfkPlayers() {
        return Collections.unmodifiableSet(this.holograms.keySet());
    }
}
