package com.eternalcode.core.feature.powertool;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Key;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

@Command(name = "powertool", aliases = {"pt"})
@Permission("eternalcore.powertool.assign")
public class PowertoolCommand {

    public static final String KEY = "powertool";
    private final NamespacedKey key;

    private final Plugin plugin;
    private final NoticeService noticeService;

    @Inject
    PowertoolCommand(Plugin plugin, NoticeService noticeService) {
        this.plugin = plugin;
        this.noticeService = noticeService;
        this.key = new NamespacedKey(this.plugin, KEY);
    }

    @Execute
    void clear(@Sender Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (item.getType() == Material.AIR || meta == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().noItemInMainHand())
                .send();
            return;
        }
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();

        if (persistentDataContainer.has(key, PersistentDataType.STRING)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().removed())
                .placeholder("{ITEM}", item.getType().name())
                .placeholder("{COMMAND}", persistentDataContainer.get(key, PersistentDataType.STRING))
                .send();

            persistentDataContainer.remove(key);
            item.setItemMeta(meta);
        } else {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().notAssigned())
                .placeholder("{ITEM}", item.getType().name())
                .send();
        }
    }

    @Execute
    void assign(@Sender Player player, @Join @Key(PowertoolCommandArgument.KEY) String command) {
        if (command.trim().isEmpty()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().emptyCommand())
                .send();
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (item.getType() == Material.AIR || meta == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().noItemInMainHand())
                .send();
            return;
        }
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        persistentDataContainer.set(key, PersistentDataType.STRING, command);
        item.setItemMeta(meta);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.powertool().assigned())
            .placeholder("{ITEM}", item.getType().name())
            .placeholder("{COMMAND}", command)
            .send();
    }
}
