package com.eternalcode.core.feature.punishment.command.argument;

import com.eternalcode.core.feature.punishment.warn.WarnService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.TranslationManager;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

@LiteArgument(type = OfflinePlayer.class, name = WarnedPlayerArgument.KEY)
public class WarnedPlayerArgument extends PunishedPlayerArgument {

    public static final String KEY = "warnedPlayer";

    @Inject
    public WarnedPlayerArgument(TranslationManager translationManager, Server server, WarnService warnService) {
        super(translationManager, server, warnService::activeWarns);
    }
}
