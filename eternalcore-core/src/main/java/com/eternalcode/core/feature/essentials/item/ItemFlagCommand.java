package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import panda.utilities.text.Formatter;

@Route(name = "itemflag")
@Permission("eternalcore.itemflag")
public class ItemFlagCommand {

    private static final Placeholders<ItemFlag> ITEM_FLAG_PLACEHOLDER = Placeholders.of("{ITEM_FLAG}", Enum::name);

    private final NoticeService noticeService;

    public ItemFlagCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Adds or removes item flag from item in hand", arguments = "<item-flag>")
    void execute(Player player, @Arg ItemFlag flag) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        ItemMeta meta = hand.getItemMeta();

        if (meta == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());
            return;
        }

        Formatter formatter = ITEM_FLAG_PLACEHOLDER.toFormatter(flag);

        if (meta.hasItemFlag(flag)) {
            meta.removeItemFlags(flag);
            this.noticeService.player(player.getUniqueId(), translation -> translation.item().itemFlagRemovedMessage(), formatter);
            return;
        }

        meta.addItemFlags(flag);
        hand.setItemMeta(meta);
        this.noticeService.player(player.getUniqueId(), translation -> translation.item().itemFlagAddedMessage(), formatter);
    }

    @Execute(route = "clear")
    @DescriptionDocs(description = "Clears all item flags from item in hand")
    void clear(Player player) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        ItemMeta meta = hand.getItemMeta();

        if (meta == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());
            return;
        }

        meta.removeItemFlags(ItemFlag.values());
        hand.setItemMeta(meta);
        this.noticeService.player(player.getUniqueId(), translation -> translation.item().itemFlagClearedMessage());
    }

}
