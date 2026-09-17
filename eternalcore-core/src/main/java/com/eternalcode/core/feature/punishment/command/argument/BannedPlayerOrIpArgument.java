package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;

import org.bukkit.command.CommandSender;

@LiteArgument(type = String.class, name = BannedPlayerOrIpArgument.KEY)
public class BannedPlayerOrIpArgument extends AbstractViewerArgument<String> {

    public static final String KEY = "targetOrIp";

    private final PunishmentService punishmentService;

    @Inject
    public BannedPlayerOrIpArgument(TranslationManager translationManager, PunishmentService punishmentService) {
        super(translationManager);
        this.punishmentService = punishmentService;
    }

    @Override
    public ParseResult<String> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        if (argument.isBlank()) {
            return ParseResult.failure(translation.argument().missingPlayerName());
        }

        return ParseResult.success(argument);
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<String> argument,
        SuggestionContext context
    ) {
        return this.punishmentService.activeBans().stream()
            .map(Punishment::target)
            .map(PunishmentTarget::name)
            .collect(SuggestionResult.collector());
    }
}
