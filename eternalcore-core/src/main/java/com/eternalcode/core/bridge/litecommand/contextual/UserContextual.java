package com.eternalcode.core.bridge.litecommand.contextual;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.context.ContextProvider;
import dev.rollczi.litecommands.context.ContextResult;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@LiteContextual(User.class)
class UserContextual implements ContextProvider<CommandSender, User> {

    private final TranslationManager translationManager;
    private final UserManager userManager;

    @Inject
    UserContextual(TranslationManager translationManager, UserManager userManager) {
        this.translationManager = translationManager;
        this.userManager = userManager;
    }

    @Override
    public ContextResult<User> provide(Invocation<CommandSender> invocation) {
        if (invocation.sender() instanceof Player player) {
            return ContextResult.ok(() -> this.userManager.getUser(player.getUniqueId())
                .orElseThrow(() -> new IllegalStateException("Player " + player.getName() + " is not registered!")));
        }

        Translation translation = this.translationManager.getDefaultMessages();
        Notice onlyPlayer = translation.argument().onlyPlayer();

        return ContextResult.error(onlyPlayer);
    }

}
