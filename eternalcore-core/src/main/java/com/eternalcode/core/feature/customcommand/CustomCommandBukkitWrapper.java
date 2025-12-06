package com.eternalcode.core.feature.customcommand;

import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import dev.rollczi.litecommands.util.StringUtil;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CustomCommandBukkitWrapper extends Command {

    private static final String EMPTY_USAGE_MESSAGE = StringUtil.EMPTY;
    private static final String EMPTY_DESCRIPTION_MESSAGE = StringUtil.EMPTY;

    private final NoticeService noticeService;
    private final Notice message;
    private final List<String> commands;

    protected CustomCommandBukkitWrapper(
            @NotNull String name,
            @NotNull List<String> aliases,
            NoticeService noticeService,
            Notice message,
            List<String> commands
    ) {
        super(name, EMPTY_DESCRIPTION_MESSAGE, EMPTY_USAGE_MESSAGE, aliases);

        this.noticeService = noticeService;
        this.message = message;
        this.commands = commands;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        this.noticeService.create()
                .notice(this.message)
                .sender(commandSender)
                .send();

        for (String command : this.commands) {
            String commandToExecute = command.startsWith("/") ? command.substring(1) : command;
            commandSender.getServer().dispatchCommand(commandSender, commandToExecute);
        }

        return true;
    }
}
