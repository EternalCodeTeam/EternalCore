package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Section(route = "hat")
@Permission("eternalcore.command.hat")
@UsageMessage("&8» &cPoprawne użycie &7/hat")
public class HatCommand {

    private final NoticeService noticeService;

    public HatCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Player player) {

        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getHelmet();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null) {
            this.noticeService
                .notice()
                .player(player.getUniqueId())
                .message(messages -> messages.argument().noItem())
                .send();
            return;
        }

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            playerInventory.setHelmet(handItem);
            playerInventory.remove(handItem); // TODO: to usunie wszystkie takie same itemy z tego co pamniętam
            return;
        }

        this.noticeService
            .notice()
            .player(player.getUniqueId())
            .message(messages -> messages.other().nullHatMessage())
            .send();
    }
}
