package com.eternalcode.core.feature.essentials.item.itemedit;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
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

    @Execute
    @DescriptionDocs(description = "Sets lore of item in hand", arguments = "<line> <text>")
    void execute(@Context Player player, @Arg(ItemLoreArgument.KEY) int line, @Join String text) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> lore = itemMeta.getLore();
        lore = lore == null ? new ArrayList<>() : new ArrayList<>(lore);

        while (lore.size() <= line) {
            lore.add("");
        }

        // Serialize using GsonComponentSerializer for modern Minecraft versions
        String json = GsonComponentSerializer.gson().serialize(
            AdventureUtil.resetItalic(this.miniMessage.deserialize(text))
        );
        lore.set(line, json);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.create()
            .notice(translation -> translation.itemEdit().itemChangeLoreMessage())
            .placeholder("{ITEM_LORE}", text)
            .player(player.getUniqueId())
            .send();
    }

    @Execute(name = "remove")
    @DescriptionDocs(description = "Removes a specific line of lore from the item in hand", arguments = "<line>")
    void remove(@Context Player player, @Arg(ItemLoreArgument.KEY) int line) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        if (lore == null || lore.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.itemEdit().noLore());
            return;
        }

        if (line < 0 || line >= lore.size()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.itemEdit().invalidLoreLine());
            return;
        }

        lore.remove(line);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        this.noticeService.player(player.getUniqueId(), translation -> translation.itemEdit().itemLoreLineRemoved());
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Clears all lore from the item in hand")
    void clear(@Context Player player) {
        ItemStack itemStack = this.validateItemFromMainHand(player);

        if (itemStack == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.argument().noItem());
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(new ArrayList<>());
        itemStack.setItemMeta(itemMeta);

        this.noticeService.player(player.getUniqueId(), translation -> translation.itemEdit().itemClearLoreMessage());
    }

    private ItemStack validateItemFromMainHand(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR || itemStack.getItemMeta() == null) {
            return null;
        }

        return itemStack;
    }
}
