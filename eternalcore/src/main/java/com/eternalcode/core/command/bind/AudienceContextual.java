package com.eternalcode.core.command.bind;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;

public class AudienceContextual implements Contextual<CommandSender, Audience> {

    private final UserManager userManager;

    public AudienceContextual(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Result<Audience, Object> extract(CommandSender sender, LiteInvocation invocation) {
        if (sender instanceof Player player) {
            Language language = this.userManager.getUser(player.getUniqueId())
                .map(user -> user.getSettings().getLanguage())
                .orElseGet(Language.DEFAULT);

            return Result.ok(Audience.player(player.getUniqueId(), language));
        }

        if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender || sender instanceof BlockCommandSender) {
            return Result.ok(Audience.console());
        }

        throw new IllegalArgumentException("Unsupported sender type: " + sender.getClass().getName());
    }

}
