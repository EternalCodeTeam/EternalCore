package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DocsDescription;
import com.eternalcode.core.notification.NoticeService;
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
    @DocsDescription(description = "Sets lore of item in hand", arguments = "<line> <text>")
    void execute(Player player, @Arg int line, @Joiner String text) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());

            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> lore = itemMeta.getLore();

        lore = lore == null ? new ArrayList<>() : new ArrayList<>(lore);

        if (text.equals("none")) {
            lore.remove(line);
        }
        else {
            // fill list
            while (lore.size() <= line) {
                lore.add("");
            }

            lore.set(line, Legacy.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(text)));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.create()
            .notice(translation -> translation.item().itemChangeLoreMessage())
            .placeholder("{ITEM_LORE}", text)
            .player(player.getUniqueId())
            .send();
    }

    @Execute(route = "clear")
    @DocsDescription(description = "Clears lore of item in hand")
    void clear(Player player) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(new ArrayList<>());
        itemStack.setItemMeta(itemMeta);

        this.noticeService.player(player.getUniqueId(), translation -> translation.item().itemClearLoreMessage());
    }

    private ItemStack validateItemFromMainHand(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR || itemStack.getItemMeta() == null) {
            return null;
        }

        return itemStack;
    }

}
