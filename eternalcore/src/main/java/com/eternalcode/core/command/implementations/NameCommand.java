package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.legacy.Legacy;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Section(route = "name", aliases = { "rename", "itemname" })
@Permission("eternalcore.command.itemname")
@UsageMessage("&8» &cPoprawne użycie &7/name <nazwa>")
public class NameCommand {

    private final NoticeService noticeService;

    public NameCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @MinArgs(1)
    public void execute(Player player, @Joiner String name) {
        ItemStack itemStack = player.getInventory().getItemInOffHand();

        if (itemStack.getType() == Material.AIR) {
            this.noticeService
                .notice()
                .message(messages -> messages.argument().noItem())
                .player(player.getUniqueId())
                .send();

            return;
        }


        ItemMeta itemMeta = itemStack.getItemMeta();
        String serialize = Legacy.SERIALIZER.serialize(ChatUtils.component(name));

        itemMeta.setDisplayName(serialize);
        itemStack.setItemMeta(itemMeta);

        this.noticeService
            .notice()
            .message(messages -> messages.other().nameMessage())
            .placeholder("{NAME}", name)
            .player(player.getUniqueId())
            .send();
    }
}
