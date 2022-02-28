/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.chat.audience.NotificationType;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Server;

@Section(route = "alert", aliases = { "broadcast", "bc" })
@Permission("eternalcore.command.alert")
@UsageMessage("&8» &cPoprawne użycie &7/alert <title/actionbar/chat> <text>")
public class AlertCommand {

    private final AudiencesService audiencesService;
    private final Server server;

    public AlertCommand(AudiencesService audiencesService, Server server) {
        this.audiencesService = audiencesService;
        this.server = server;
    }

    @Execute
    @MinArgs(2)
    public void execute(@Arg(0) NotificationType type, @Joiner String text) {
        audiencesService.notice()
            .all()
            .notice(type, messages -> messages.other().alertMessagePrefix())
            .placeholder("{BROADCAST}", text)
            .send();
    }
}
