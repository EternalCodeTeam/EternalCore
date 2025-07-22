package com.eternalcode.core.feature.customcommand;

import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.Notice;
import dev.rollczi.litecommands.util.StringUtil;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CustomCommandBukkitWrapper extends Command {

    private final NoticeService noticeService;

    private final Notice message;

    protected CustomCommandBukkitWrapper(
        @NotNull String name,
        @NotNull String description,
        @NotNull List<String> aliases,
        NoticeService noticeService,
        Notice message
    ) {
        super(name, description, StringUtil.EMPTY, aliases);
        // empty is usage message

        this.noticeService = noticeService;
        this.message = message;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        this.noticeService.create()
            .notice(this.message)
            .sender(commandSender)
            .send();

        return true;
    }
}
