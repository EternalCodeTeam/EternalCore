package com.eternalcode.core.feature.punishment.command.argument;

import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.TranslationManager;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

@LiteArgument(type = OfflinePlayer.class, name = MutedPlayerArgument.KEY)
public class MutedPlayerArgument extends PunishedPlayerArgument {

    public static final String KEY = "mutedPlayer";

    @Inject
    public MutedPlayerArgument(TranslationManager translationManager, Server server, PunishmentService punishmentService) {
        super(translationManager, server, punishmentService::activeMutes);
    }
}
