package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class HelpOpCommand {

    private final ConfigurationManager configurationManager;

    public HelpOpCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "helpop",
        aliases = {"report"},
        permission = "eternalcore.command.helpop",
        usage = "&8» &cPoprawne użycie &7/helpop <text>",
        acceptsExceeded = true
    )

    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        when(args.length < 1, commandInfo.getUsageMessage());
        String text = StringUtils.join(args, " ", 1, args.length);

        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        Bukkit.getOnlinePlayers().stream().filter(players ->
            players.hasPermission("eternalcode.helpop.spy") || players.isOp()).forEach(players ->
            players.sendMessage(config.helpOpFormat.replace("{TEXT}", text)));
        sender.sendMessage(config.helpOpSend);
    }
}
