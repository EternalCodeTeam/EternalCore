package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;

@FunnyComponent
public final class ScoreboardCommand {
    @FunnyCommand(
        name = "scoreboard",
        permission = "eternalcore.command.scoreboard",
        usage = "&8» &cPoprawne użycie &7/scoreboard",
        acceptsExceeded = true
    )
    public void execute(Player player) {
        EternalCore.getInstance().getScoreboardManager().toggleScoreboard(player);
    }
}
