package com.eternalcode.core.command.implementations;


import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;

@Section(route = "vanish")
@Permission("eternalcore.command.vanish")
@UsageMessage("&8» &cPoprawne użycie &7/vanish")
public class VanishCommand {

    private final MessagesConfiguration messages;
    private final PluginConfiguration config;

    public VanishCommand(final MessagesConfiguration messages, final PluginConfiguration config) {
        this.messages = messages;
        this.config = config;
    }

    @Execute
    public void execute(final Player player) {
        player.setInvisible(!player.isInvisible());
        player.sendMessage(ChatUtils.color(this.messages.otherMessages.vanish).replace("{STATE}", player.isInvisible() ? this.config.format.disabled : this.config.format.enabled));
    }

}
