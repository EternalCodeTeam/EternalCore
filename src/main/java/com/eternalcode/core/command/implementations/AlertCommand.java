/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserService;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.resources.ValidationException;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import panda.std.stream.PandaStream;

import java.util.function.Consumer;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class AlertCommand {

    private final ConfigurationManager configurationManager;

    public AlertCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }


    @FunnyCommand(
        name = "alert",
        aliases = {"broadcast", "bc"},
        permission = "eternalcore.command.alert",
        usage = "&8» &cPoprawne użycie &7/alert <title/actionbar/chat> <text>",
        acceptsExceeded = true
    )
    public void execute(UserService service, String[] args, CommandInfo commandInfo) {
        when(args.length < 2, commandInfo.getUsageMessage());
        String text = StringUtils.join(args, " ", 1, args.length);

        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        Consumer<User> consumer = switch (args[0].toLowerCase()) {
            case "title" -> user -> user.subTitle(config.alertMessagePrefix.replace("{BROADCAST}", text), 1, 80, 1);
            case "actionbar" -> user -> user.actionBar(config.alertMessagePrefix.replace("{BROADCAST}", text));
            case "chat" -> user -> user.message(config.alertMessagePrefix.replace("{BROADCAST}", text));
            default -> throw new ValidationException(commandInfo.getUsageMessage());
        };

        PandaStream.of(Bukkit.getOnlinePlayers())
            .flatMap(service::getUser)
            .forEach(consumer);
    }
}
