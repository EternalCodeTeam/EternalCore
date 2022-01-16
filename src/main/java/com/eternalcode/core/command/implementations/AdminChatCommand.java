package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.chat.ChatUtils;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class AdminChatCommand {

    private final ConfigurationManager configurationManager;

    public AdminChatCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "adminchat",
        aliases = {"ac"},
        permission = "eternalcore.command.adminchat",
        usage = "&8» &cPoprawne użycie &7/adminchat <text>",
        acceptsExceeded = true
    )

    public void execute(String[] args, CommandInfo commandInfo) {
        when(args.length < 1, commandInfo.getUsageMessage());
        String text = StringUtils.join(args, " ", 0, args.length);

        MessagesConfiguration config = configurationManager.getMessagesConfiguration();

        String adminChatFormat = ChatUtils.color(config.adminChatFormat.replace("{TEXT}", text));

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("eternalcore.adminchat.spy")) {
                players.sendMessage(adminChatFormat);
            }
        }
    }
}
