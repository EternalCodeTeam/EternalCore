package com.eternalcode.core.feature.powertool;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

@Controller
public class PowertoolController implements Listener {

    private final Plugin plugin;

    @Inject
    PowertoolController(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || !event.getItem().hasItemMeta()) {
            return;
        }

        PersistentDataContainer dataContainer = event.getItem().getItemMeta().getPersistentDataContainer();

        NamespacedKey key = NamespacedKey.fromString(PowertoolCommand.KEY, this.plugin);

        if (dataContainer.has(key)) {
            String command = dataContainer.get(key, PersistentDataType.STRING);
            if (command != null && !command.isEmpty()) {
                event.getPlayer().performCommand(command);
            }
        }
    }
}
