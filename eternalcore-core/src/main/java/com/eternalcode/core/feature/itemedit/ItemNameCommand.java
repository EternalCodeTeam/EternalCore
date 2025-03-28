package com.eternalcode.core.feature.itemedit;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Command(name = "itemname", aliases = { "itemrename" })
@Permission("eternalcore.itemname")
class ItemNameCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;

    @Inject
    ItemNameCommand(NoticeService noticeService, MiniMessage miniMessage) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
    }

    @Execute
    @DescriptionDocs(description = "Sets name of item in hand", arguments = "<name>")
    void execute(@Context Player player, @Join String name) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        String serialized = AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(name));

        itemMeta.setDisplayName(serialized);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.create()
            .notice(translation -> translation.itemEdit().itemChangeNameMessage())
            .placeholder("{ITEM_NAME}", name)
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Clears name of item in hand")
    void clear(@Context Player player) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(null);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.player(player.getUniqueId(), translation -> translation.itemEdit().itemClearNameMessage());
    }

    private ItemStack validateItemFromMainHand(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR || itemStack.getItemMeta() == null) {
            return null;
        }

        return itemStack;
    }
}
