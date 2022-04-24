package com.eternalcode.core.command.bind;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.bind.Parameter;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public class AudienceBind implements Parameter {

    private final UserManager userManager;

    public AudienceBind(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Object apply(LiteInvocation invocation) {
        Object sender = invocation.sender().getSender();

        if (sender instanceof Player player) {
            Language language = this.userManager.getUser(player.getUniqueId())
                .map(user -> user.getSettings().getLanguage())
                .orElseGet(Language.DEFAULT);

            return Audience.player(player.getUniqueId(), language);
        }

        if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender || sender instanceof BlockCommandSender) {
            return Audience.console();
        }

        throw new IllegalArgumentException("Unsupported sender type: " + sender.getClass().getName());
    }
}
