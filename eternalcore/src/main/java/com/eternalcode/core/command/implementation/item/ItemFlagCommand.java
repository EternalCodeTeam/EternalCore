package com.eternalcode.core.command.implementation.item;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
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

    private final static Placeholders<ItemFlag> ITEM_FLAG_PLACEHOLDER = Placeholders.of("{ITEM_FLAG}", Enum::name);

    private final NoticeService noticeService;

    public ItemFlagCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player, @Arg @Name("flag") ItemFlag flag) {
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
