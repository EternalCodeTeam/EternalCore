package com.eternalcode.core.command.implementation;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.ItemUtil;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Route(name = "hat")
@Permission("eternalcore.hat")
public class HatCommand {

    private final NoticeService noticeService;

    public HatCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getHelmet();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null) {
            this.noticeService
                .create()
                .player(player.getUniqueId())
                .notice(translation -> translation.argument().noItem())
                .send();

            return;
        }

        if (itemStack != null) {
            ItemUtil.giveItem(player, itemStack);
        }

        playerInventory.setHelmet(handItem);

        ItemUtil.removeItem(player, handItem.getType(), 1);
    }
}
