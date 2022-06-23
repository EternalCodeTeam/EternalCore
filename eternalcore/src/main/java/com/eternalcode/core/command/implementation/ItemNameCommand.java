package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.util.legacy.Legacy;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Section(route = "itemname", aliases = { "itemrename" })
@Permission("eternalcore.itemname")
public class ItemNameCommand {

    private final NoticeService noticeService;

    public ItemNameCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Min(1)
    public void execute(Player player, @Joiner String name) {
        ItemStack itemStack = player.getInventory().getItemInOffHand();

        if (itemStack.getType() == Material.AIR) {
            this.noticeService
                .create()
                .message(messages -> messages.argument().noItem())
                .player(player.getUniqueId())
                .send();

            return;
        }


        ItemMeta itemMeta = itemStack.getItemMeta();
        String serialize = Legacy.SECTION_SERIALIZER.serialize(Legacy.component(name));

        itemMeta.setDisplayName(serialize);
        itemStack.setItemMeta(itemMeta);

        this.noticeService
            .create()
            .message(messages -> messages.other().nameMessage())
            .placeholder("{NAME}", name)
            .player(player.getUniqueId())
            .send();
    }
}
