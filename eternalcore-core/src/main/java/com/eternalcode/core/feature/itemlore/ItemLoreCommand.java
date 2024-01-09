package com.eternalcode.core.feature.itemlore;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.AdventureUtil;
import com.eternalcode.core.bridge.adventure.legacy.Legacy;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Command(name = "itemlore")
@Permission("eternalcore.itemlore")
class ItemLoreCommand {

    private final NoticeService noticeService;
    private final MiniMessage miniMessage;

    @Inject
    ItemLoreCommand(NoticeService noticeService, MiniMessage miniMessage) {
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
    }

    @Execute(name = "set")
    @DescriptionDocs(description = "Sets lore of item in hand", arguments = "<line> <text>")
    void execute(@Context Player player, @Arg int line, @Join String text) {
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

            lore.set(line, Legacy.SECTION_SERIALIZER.serialize(AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(text))));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.create()
            .notice(translation -> translation.item().itemChangeLoreMessage())
            .placeholder("{ITEM_LORE}", text)
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Clears lore of item in hand")
    void clear(@Context Player player) {
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
