package com.eternalcode.core.command.implementation.item;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.util.legacy.Legacy;
import dev.rollczi.litecommands.argument.Arg;
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

import java.util.ArrayList;
import java.util.List;

@Route(name = "itemlore")
@Permission("eternalcore.itemlore")
public class ItemLoreCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;

    public ItemLoreCommand(NoticeService noticeService, MiniMessage miniMessage) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
    }

    @Execute
    @Min(2)
    void execute(Player player, @Arg Integer line, @Joiner String name) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemStack.getType() == Material.AIR || itemMeta == null) {
            this.noticeService.player(player.getUniqueId(), messages -> messages.argument().noItem());
            return;
        }

        List<String> lore = itemMeta.getLore();

        lore = lore == null ? new ArrayList<>() : new ArrayList<>(lore);

        if (name.equals("none")) {
            lore.remove((int) line);
        }
        else {
            // fill list
            while (lore.size() <= line) {
                lore.add("");
            }

            lore.set(line, Legacy.SECTION_SERIALIZER.serialize(miniMessage.deserialize(name)));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.create()
            .message(messages -> messages.other().itemChangeLoreMessage())
            .placeholder("{ITEM_LORE}", name)
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

        itemMeta.setLore(List.of());
        itemStack.setItemMeta(itemMeta);

        this.noticeService.player(player.getUniqueId(), messages -> messages.other().itemClearLoreMessage());
    }

}
