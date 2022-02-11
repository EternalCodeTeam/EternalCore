package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "repair", aliases = "napraw")
@Permission("eternalcore.command.repair")
public class RepairCommand {

    private final MessagesConfiguration messages;

    public RepairCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    public void repair(Player player) {
        ItemStack handItem = player.getItemInUse();

        if (handItem.getType().isBlock() || handItem.getType().isAir()) {
            player.sendMessage(ChatUtils.color(this.messages.argumentSection.noItem));
            return;
        }
        player.getItemInUse().setDurability((short) 0);

        player.sendMessage(ChatUtils.color(this.messages.otherMessages.repairMessage));
    }

    @Execute(route = "all")
    public void repairAll(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                itemStack.setDurability((short) 0);
            }
        }

        player.sendMessage(ChatUtils.color(this.messages.otherMessages.repairMessage));
    }

    @Execute(route = "armor")
    public void repairArmor(Player player) {
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack != null) {
                itemStack.setDurability((short) 0);
            }
        }

        player.sendMessage(ChatUtils.color(this.messages.otherMessages.repairMessage));
    }
}
