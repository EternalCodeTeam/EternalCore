package com.eternalcode.core.feature.ignore;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.command.CommandSender;

@LiteArgument(type = User.class, name = "ignored_player")
class IgnoredPlayerArgument extends AbstractViewerArgument<User> {

    private static final UUID IGNORE_ALL = UUID.nameUUIDFromBytes("*".getBytes());

    private final IgnoreService ignoreService;
    private final UserManager userManager;

    @Inject
    IgnoredPlayerArgument(
        TranslationManager translationManager,
        IgnoreService ignoreService,
        UserManager userManager
    ) {
        super(translationManager);
        this.ignoreService = ignoreService;
        this.userManager = userManager;
    }

    @Override
    public ParseResult<User> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        return this.userManager.getUser(argument)
            .map(ParseResult::success)
            .orElseGet(() -> ParseResult.failure(translation.argument().offlinePlayer()));
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<User> argument,
        SuggestionContext context) {
        CommandSender sender = invocation.sender();

        if (!(sender instanceof org.bukkit.entity.Player)) {
            return SuggestionResult.empty();
        }

        UUID senderUuid = ((org.bukkit.entity.Player) sender).getUniqueId();

        try {
            Set<UUID> ignoredPlayers = this.ignoreService.getIgnoredPlayers(senderUuid).get();

            return ignoredPlayers.stream()
                .filter(uuid -> !uuid.equals(IGNORE_ALL)) // Filter out the "ignore all" special UUID
                .map(this.userManager::getUser)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(User::getName)
                .collect(SuggestionResult.collector());
        }
        catch (Exception exception) {
            return SuggestionResult.empty();
        }
    }
}
