package com.eternalcode.core.command.contextual;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;

public class PlayerContextual implements Contextual<CommandSender, Player> {

    private final LanguageManager languageManager;

    public PlayerContextual(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    @Override
    public Result<Player, Object> extract(CommandSender sender, Invocation<CommandSender> invocation) {
        if (sender instanceof Player player) {
            return Result.ok(player);
        }

        Messages messages = this.languageManager.getDefaultMessages();
        String onlyPlayer = messages.argument().onlyPlayer();

        return Result.error(onlyPlayer);
    }

}
