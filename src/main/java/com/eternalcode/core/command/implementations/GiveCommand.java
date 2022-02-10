package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.helpers.builders.ItemBuilder;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "give", aliases = {"i", "item"})
@Permission("eternalcore.commands.give")
@UsageMessage("&8» &cPoprawne użycie &7/give <materiał> [gracz]")
public class GiveCommand {

    private final EternalCore eternalCore;

    public GiveCommand(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
    }

    @Execute
    @Required(2)
    public void execute(@Arg(0) Player player, @Arg(1) Material material) {
        Bukkit.getScheduler().runTaskAsynchronously(eternalCore, () -> {
            ItemStack item = new ItemBuilder(material).build();

            player.getInventory().addItem(item);
        });
    }

}
