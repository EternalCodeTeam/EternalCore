package com.eternalcode.core.command.contextual;

import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;

public class PlayerContextual implements Contextual<CommandSender, Player> {

    private final TranslationManager translationManager;

    public PlayerContextual(TranslationManager translationManager) {
        this.translationManager = translationManager;
    }

    @Override
    public Result<Player, Object> extract(CommandSender sender, Invocation<CommandSender> invocation) {
        if (sender instanceof Player player) {
            return Result.ok(player);
        }

        Translation translation = this.translationManager.getDefaultMessages();
        Notice onlyPlayer = translation.argument().onlyPlayer();

        return Result.error(onlyPlayer);
    }

}
