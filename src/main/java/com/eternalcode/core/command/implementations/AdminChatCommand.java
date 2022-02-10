package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;

@Section(route = "adminchat")
@Permission("eternalcore.adminchat")
@UsageMessage("&8» &cPoprawne użycie &7/adminchat <text>")
public class AdminChatCommand {

    @Execute
    @MinArgs(1)
    public void execute(String[] args, MessagesConfiguration message) {
        String text = StringUtils.join(args, " ", 0, args.length);

        Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("eternalcore.adminchat.spy")).forEach(players ->
            players.sendMessage(ChatUtils.color(message.messagesSection.adminChatFormat.replace("{TEXT}", text))));
    }
}
