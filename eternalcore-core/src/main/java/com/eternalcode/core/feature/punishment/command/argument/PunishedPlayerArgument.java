package com.eternalcode.core.feature.punishment.command.argument;

import com.eternalcode.core.feature.punishment.KnownPlayerResolver;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;

import java.util.List;
import java.util.function.Supplier;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public abstract class PunishedPlayerArgument extends AbstractViewerArgument<OfflinePlayer> {

    private final KnownPlayerResolver knownPlayerResolver;
    private final Supplier<? extends List<? extends Punishment>> activePunishmentsSupplier;

    protected PunishedPlayerArgument(
        TranslationManager translationManager,
        KnownPlayerResolver knownPlayerResolver,
        Supplier<? extends List<? extends Punishment>> activePunishmentsSupplier
    ) {
        super(translationManager);
        this.knownPlayerResolver = knownPlayerResolver;
        this.activePunishmentsSupplier = activePunishmentsSupplier;
    }

    @Override
    public ParseResult<OfflinePlayer> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        if (argument.isBlank()) {
            return ParseResult.failure(translation.argument().missingPlayerName());
        }

        return this.knownPlayerResolver.resolve(argument)
            .map(ParseResult::success)
            .orElseGet(() -> ParseResult.failure(translation.argument().missingPlayer()));
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<OfflinePlayer> argument,
        SuggestionContext context
    ) {
        return this.activePunishmentsSupplier.get().stream()
            .map(Punishment::target)
            .map(PunishmentTarget::name)
            .distinct()
            .collect(SuggestionResult.collector());
    }
}
