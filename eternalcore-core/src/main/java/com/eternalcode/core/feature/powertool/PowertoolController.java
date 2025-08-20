package com.eternalcode.core.feature.powertool;

import static com.eternalcode.core.feature.powertool.PowertoolCommand.KEY;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.multification.shared.Formatter;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

@Controller
@PermissionDocs(
    name = "Use powertools",
    permission = "eternalcore.powertool.use",
    description = "Allows the player to use powertools, which are items that execute commands when left/right-clicked."
)
public class PowertoolController implements Listener {

    private static final String POWERTOOL_USE_PERMISSION = "eternalcore.powertool.use";
    private static final Placeholders<Player> EXECUTION_CONTEXT_PLACEHOLDERS = Placeholders.<Player>builder()
        .with("{PLAYER}", Player::getName)
        .with("{WORLD}", player -> player.getWorld().getName())
        .with("{X}", player -> String.valueOf(player.getLocation().getBlockX()))
        .with("{Y}", player -> String.valueOf(player.getLocation().getBlockY()))
        .with("{Z}", player -> String.valueOf(player.getLocation().getBlockZ()))
        .with("{YAW}", player -> String.valueOf(player.getLocation().getYaw()))
        .with("{PITCH}", player -> String.valueOf(player.getLocation().getPitch()))
        .with("{ITEM}", player -> player.getInventory().getItemInMainHand().getItemMeta().getItemName())
        .with("{ITEM_TYPE}", player -> player.getInventory().getItemInMainHand().getType().name())
        .build();

    private final Plugin plugin;

    @Inject
    PowertoolController(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getItemMeta() == null) {
            return;
        }

        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();

        NamespacedKey key = NamespacedKey.fromString(KEY, this.plugin);

        if (dataContainer.has(key)) {
            String command = dataContainer.get(key, PersistentDataType.STRING);
            if (command != null && !command.isEmpty()) {
                Player player = event.getPlayer();
                if (!player.hasPermission(POWERTOOL_USE_PERMISSION)) {
                    dataContainer.remove(key);
                    return;
                }
                Formatter formatter = EXECUTION_CONTEXT_PLACEHOLDERS.toFormatter(player);

                player.performCommand(formatter.format(command));
            }
        }
    }
}
