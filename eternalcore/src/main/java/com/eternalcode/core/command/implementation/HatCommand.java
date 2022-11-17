package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Material;
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
                .message(messages -> messages.argument().noItem())
                .send();

            return;
        }

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            playerInventory.setHelmet(handItem);
            playerInventory.removeItem(handItem);

            return;
        }

        this.noticeService
            .create()
            .player(player.getUniqueId())
            .message(messages -> messages.other().nullHatMessage())
            .send();
    }
}
