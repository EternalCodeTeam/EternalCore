package com.eternalcode.core.feature.punishment.command.argument;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.litecommand.argument.OfflinePlayerArgument;
import com.eternalcode.core.translation.TranslationManager;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.Supplier;

public abstract class PunishedPlayerArgument extends OfflinePlayerArgument {

    private final Supplier<List<Punishment>> activePunishmentsSupplier;

    protected PunishedPlayerArgument(
        TranslationManager translationManager,
        Server server,
        Supplier<List<Punishment>> activePunishmentsSupplier
    ) {
        super(translationManager, server);
        this.activePunishmentsSupplier = activePunishmentsSupplier;
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
            .collect(SuggestionResult.collector());
    }
}
