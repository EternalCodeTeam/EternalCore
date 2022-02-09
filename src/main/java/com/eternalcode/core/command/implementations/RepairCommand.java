package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "repair", aliases = "napraw")
@Permission("eternalcore.command.repair")
public class RepairCommand {

    @Execute
    public void repair(Player player, MessagesConfiguration messages) {
        ItemStack handItem = player.getItemInUse();

        if (handItem.getType().isBlock() || handItem.getType().isAir()) {
            player.sendMessage(ChatUtils.color(messages.messagesSection.noItem));
            return;
        }
        player.getItemInUse().setDurability((short) 0);

        player.sendMessage(ChatUtils.color(messages.messagesSection.repairMessage));
    }

    @Execute(route = "all")
    public void repairAll(Player player, MessagesConfiguration messages) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                itemStack.setDurability((short) 0);
            }
        }

        player.sendMessage(ChatUtils.color(messages.messagesSection.repairMessage));
    }

    @Execute(route = "armor")
    public void repairArmor(Player player, MessagesConfiguration messages) {
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack != null) {
                itemStack.setDurability((short) 0);
            }
        }

        player.sendMessage(ChatUtils.color(messages.messagesSection.repairMessage));
    }
}
