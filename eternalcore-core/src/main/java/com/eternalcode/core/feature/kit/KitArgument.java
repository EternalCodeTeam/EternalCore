package com.eternalcode.core.feature.kit;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;

import java.util.Optional;

@LiteArgument(type = Kit.class)
public class KitArgument extends ArgumentResolver<CommandSender, Kit> {

    private final KitService kitService;

    @Inject
    public KitArgument(KitService kitService) {
        this.kitService = kitService;
    }

    @Override
    protected ParseResult<Kit> parse(Invocation<CommandSender> invocation, Argument<Kit> context, String argument) {
        Optional<Kit> kitOptional = this.kitService.getKit(argument);

        if (kitOptional.isEmpty()) {
            return ParseResult.failure("Kit '" + argument + "' does not exist!");
        }

        return ParseResult.success(kitOptional.get());
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Kit> argument, SuggestionContext context) {
        return this.kitService.getPermissionsList().values().stream()
            .map(Kit::name)
            .collect(SuggestionResult.collector());
    }
}