package com.eternalcode.core.command.implementations;


import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "vanish")
@Permission("eternalcore.command.vanish")
@UsageMessage("&8» &cPoprawne użycie &7/vanish")
public class VanishCommand {

    @Execute
    public void execute(final CommandSender sender) {
        ((Player) sender).setInvisible(!((Player) sender).isInvisible());
        sender.sendMessage(ChatUtils.color("&8» &aVanish został &7" + (((Player) sender).isInvisible() ? "włączony" : "wyłączony")));
    }

}
