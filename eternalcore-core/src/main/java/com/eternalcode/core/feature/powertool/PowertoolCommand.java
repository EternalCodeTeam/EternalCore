package com.eternalcode.core.feature.powertool;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
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

    private final Plugin plugin;
    private final NoticeService noticeService;

    @Inject
    PowertoolCommand(Plugin plugin, NoticeService noticeService) {
        this.plugin = plugin;
        this.noticeService = noticeService;
    }

    @Execute
    void clear(@Sender Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getItemMeta() == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().noItemInMainHand())
                .send();
            return;
        }
        PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();

        if (persistentDataContainer.has(new NamespacedKey(this.plugin, KEY), PersistentDataType.STRING)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().commandRemoved())
                .placeholder("{ITEM}", item.getType().name())
                .placeholder("{COMMAND}", persistentDataContainer.get(new NamespacedKey(this.plugin, KEY), PersistentDataType.STRING))
                .send();

            persistentDataContainer.remove(new NamespacedKey(this.plugin, KEY));
        } else {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().commandNotAssigned())
                .placeholder("{ITEM}", item.getType().name())
                .send();
        }
    }

    @Execute
    void assign(@Sender Player player, @Join String command) {
        if (command.isEmpty()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().commandCannotBeEmpty())
                .send();
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.powertool().noItemInMainHand())
                .send();
            return;
        }
        // TODO: properly set PDC data (we can't access {@link ItemMeta#editPersistentDataContainer} directly since
        //  it's paper only method)
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey(this.plugin, KEY), PersistentDataType.STRING, command);
        item.setItemMeta(meta);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.powertool().commandAssigned())
            .placeholder("{ITEM}", item.getType().name())
            .placeholder("{COMMAND}", command)
            .send();
    }
}
