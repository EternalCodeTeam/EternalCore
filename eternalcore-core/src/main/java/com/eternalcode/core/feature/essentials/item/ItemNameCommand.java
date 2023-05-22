package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.AdventureUtil;
import com.eternalcode.core.util.legacy.Legacy;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
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
    @DescriptionDocs(description = "Sets name of item in hand", arguments = "<name>")
    void execute(Player player, @Joiner String name) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());

            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        String serialized = Legacy.SECTION_SERIALIZER.serialize(AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(name)));

        itemMeta.setDisplayName(serialized);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.create()
            .notice(translation -> translation.item().itemChangeNameMessage())
            .placeholder("{ITEM_NAME}", name)
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "clear")
    @DescriptionDocs(description = "Clears name of item in hand")
    void clear(Player player) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(null);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.player(player.getUniqueId(), translation -> translation.item().itemClearNameMessage());
    }

    private ItemStack validateItemFromMainHand(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR || itemStack.getItemMeta() == null) {
            return null;
        }

        return itemStack;
    }

}
