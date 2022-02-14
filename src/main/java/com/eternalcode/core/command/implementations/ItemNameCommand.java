package com.eternalcode.core.command.implementations;


import com.eternalcode.core.command.argument.StringPlayerArgument;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.*;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import panda.std.Option;

@Section(route = "itemname")
@Permission("eternalcore.command.itemname")
@UsageMessage("&8» &cPoprawne użycie &7/itemname <nazwa>")
public class ItemNameCommand {


    @Execute
    @MinArgs(1)
    public void execute(final Player player, @Arg(0) @Handler(StringPlayerArgument.class) final Option<String> stringOption) {
        final ItemStack itemStack = player.getItemInUse();
        final ItemMeta meta = itemStack.getItemMeta();
        final Component newDisplayName = Component.text(String.valueOf(stringOption));
        meta.displayName(newDisplayName);
        // TODO add messagesConfiguration here
        player.sendMessage(ChatUtils.color("&8» &aNazwa twojego przedmiotu została pomyślnie zmieniona."));
    }

}
