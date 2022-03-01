package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
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
    
    private final AudiencesService audiencesService;
    
    public NameCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute @MinArgs(1)
    public void execute(Player player, @Joiner String name) {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null || handItem.getType() == Material.AIR) {
            this.audiencesService
                .notice()
                .message(messages -> messages.argument().noItem())
                .player(player.getUniqueId())
                .send();

            return;
        }

        handItem.editMeta(itemMeta -> itemMeta.displayName(ChatUtils.component(name)));

        this.audiencesService
            .notice()
            .message(messages -> messages.other().nameMessage())
            .placeholder("{NAME}", name)
            .player(player.getUniqueId())
            .send();
    }
}
