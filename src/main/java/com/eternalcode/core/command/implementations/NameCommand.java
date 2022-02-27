package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
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
import org.bukkit.inventory.PlayerInventory;

@Section(route = "name", aliases = { "rename", "itemname" })
@Permission("eternalcore.command.itemname")
@UsageMessage("&8» &cPoprawne użycie &7/name <nazwa>")
public class NameCommand {
    
    private final MessagesConfiguration messages;
    
    public NameCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute @MinArgs(1)
    public void execute(Player player, @Joiner String name) {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null || handItem.getType() == Material.AIR) {
            player.sendMessage(ChatUtils.color(this.messages.argumentSection.noItem));
            return;
        }

        handItem.editMeta(itemMeta -> itemMeta.displayName(ChatUtils.component(name)));

        player.sendMessage(ChatUtils.color(this.messages.otherMessages.nameMessage.replace("{NAME}", name)));
    }
}
