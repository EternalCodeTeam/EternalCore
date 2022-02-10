package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.helpers.builders.ItemBuilder;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "give", aliases = { "i", "item" })
@Permission("eternalcore.commands.give")
@UsageMessage("&8» &cPoprawne użycie &7/give <materiał> [gracz]")
public class GiveCommand {

    private final EternalCore eternalCore;
    private final Server server;

    public GiveCommand(EternalCore eternalCore, Server server) {
        this.eternalCore = eternalCore;
        this.server = server;
    }

    @Execute
    @Required(2)
    public void execute(@Arg(0) Player player, @Arg(1) Material material) {
        server.getScheduler().runTaskAsynchronously(eternalCore, () -> {
            ItemStack item = new ItemBuilder(material).build();

            player.getInventory().addItem(item);
        });
    }

}
