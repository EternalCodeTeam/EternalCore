package com.eternalcode.core.command.implementation.item;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.util.legacy.Legacy;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Route(name = "itemname", aliases = { "itemrename" })
@Permission("eternalcore.itemname")
public class ItemNameCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;

    public ItemNameCommand(NoticeService noticeService, MiniMessage miniMessage) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
    }

    @Execute
    @Min(1)
    void execute(Player player, @Joiner String name) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemStack.getType() == Material.AIR || itemMeta == null) {
            this.noticeService.player(player.getUniqueId(), messages -> messages.argument().noItem());
            return;
        }

        String serialized = Legacy.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(name));

        itemMeta.setDisplayName(serialized);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.create()
            .message(messages -> messages.other().itemChangeNameMessage())
            .placeholder("{ITEM_NAME}", name)
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "clear")
    void clear(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemStack.getType() == Material.AIR || itemMeta == null) {
            this.noticeService.player(player.getUniqueId(), messages -> messages.argument().noItem());
            return;
        }

        itemMeta.setDisplayName(null);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.player(player.getUniqueId(), messages -> messages.other().itemClearNameMessage());
    }

}
