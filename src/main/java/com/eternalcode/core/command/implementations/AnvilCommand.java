/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section (route = "anvil", aliases = { "kowadlo", "kowadło" })
@Permission ("eternalcore.command.anvil")
public class AnvilCommand {

    @Execute
    public void execute (MessagesConfiguration messages, Player player) {
        player.openAnvil(null, true);
        player.sendMessage(ChatUtils.color(messages.messagesSection.anvilGuiOpenMessage));
    }
}
