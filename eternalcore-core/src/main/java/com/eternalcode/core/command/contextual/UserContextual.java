package com.eternalcode.core.command.contextual;

import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;

@LiteContextual(User.class)
public class UserContextual implements Contextual<CommandSender, User> {

    private final TranslationManager translationManager;
    private final UserManager userManager;

    public UserContextual(TranslationManager translationManager, UserManager userManager) {
        this.translationManager = translationManager;
        this.userManager = userManager;
    }

    @Override
    public Result<User, Object> extract(CommandSender sender, Invocation<CommandSender> invocation) {
        if (sender instanceof Player player) {
            return Result.ok(this.userManager.getUser(player.getUniqueId())
                .orElseThrow(() -> new IllegalStateException("Player " + player.getName() + " is not registered!")));
        }

        Translation translation = this.translationManager.getDefaultMessages();
        Notice onlyPlayer = translation.argument().onlyPlayer();

        return Result.error(onlyPlayer);
    }

}
