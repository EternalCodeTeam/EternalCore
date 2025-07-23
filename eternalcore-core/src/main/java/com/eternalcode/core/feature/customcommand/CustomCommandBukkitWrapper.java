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

    protected CustomCommandBukkitWrapper(
            @NotNull String name,
            @NotNull List<String> aliases,
            NoticeService noticeService,
            Notice message
    ) {
        super(name, EMPTY_DESCRIPTION_MESSAGE, EMPTY_USAGE_MESSAGE, aliases);

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
