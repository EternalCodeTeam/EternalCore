package com.eternalcode.core.feature.powertool;

import static com.eternalcode.core.feature.powertool.PowertoolCommand.KEY;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.multification.shared.Formatter;
import java.util.logging.Logger;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
        .with("{X}", player -> Integer.toString(player.getLocation().getBlockX()))
        .with("{Y}", player -> Integer.toString(player.getLocation().getBlockY()))
        .with("{Z}", player -> Integer.toString(player.getLocation().getBlockZ()))
        .with("{YAW}", player -> Float.toString(player.getLocation().getYaw()))
        .with("{PITCH}", player -> Float.toString(player.getLocation().getPitch()))
        .with("{ITEM}", player -> player.getInventory().getItemInMainHand().getItemMeta().getItemName())
        .with("{ITEM_TYPE}", player -> player.getInventory().getItemInMainHand().getType().name())
        .build();

    private final Plugin plugin;
    private final Logger logger;
    private final NoticeService noticeService;

    @Inject
    PowertoolController(Plugin plugin, NoticeService noticeService) {
        this.plugin = plugin;
        this.logger = this.plugin.getLogger();
        this.noticeService = noticeService;
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getItemMeta() == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        NamespacedKey key = NamespacedKey.fromString(KEY, this.plugin);

            String command = dataContainer.get(key, PersistentDataType.STRING);
            if (command != null && !command.trim().isEmpty()) {
                Player player = event.getPlayer();
                if (!player.hasPermission(POWERTOOL_USE_PERMISSION)) {
                    dataContainer.remove(key);
                    item.setItemMeta(meta);
                    return;
                }
                Formatter formatter = EXECUTION_CONTEXT_PLACEHOLDERS.toFormatter(player);

                String formattedCommand = formatter.format(command);
                if (player.performCommand(formattedCommand)) {
                    this.logger.info("Player " + player.getName() + " used powertool command: /" + formattedCommand);
                    return;
                }
                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.powertool().executionFailed())
                    .placeholder("{COMMAND}", formattedCommand)
                    .send();
            }
    }
}

