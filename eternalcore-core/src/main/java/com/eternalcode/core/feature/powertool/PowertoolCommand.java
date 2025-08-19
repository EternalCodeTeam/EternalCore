package com.eternalcode.core.feature.powertool;

import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

@Command(name = "powertool", aliases = {"pt"})
@Permission("eternalcore.powertool")
public class PowertoolCommand {

    public static final String KEY = "powertool";

    private final Plugin plugin;

    @Inject
    PowertoolCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Execute
    void clear(@Sender Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();

        if (persistentDataContainer.has(new NamespacedKey(this.plugin, KEY), PersistentDataType.STRING)) {
            persistentDataContainer.remove(new NamespacedKey(this.plugin, KEY));
            player.sendMessage("Power tool command cleared."); // TODO: Do not use hardcoded messages, use a translation system
        } else {
            player.sendMessage("This item is not a power tool.");
        }
    }

    @Execute
    void assign(@Sender Player player, @Join @Arg String command) {
        ItemStack item = player.getInventory().getItemInMainHand();
        PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey(this.plugin, KEY), PersistentDataType.STRING, command);
    }

    @Execute
    void assignConsole(@Sender ConsoleCommandSender sender, @Arg Player player, @Join @Arg String command) {
        this.assign(player, command);

        // send confirmation message to sender
    }

}
