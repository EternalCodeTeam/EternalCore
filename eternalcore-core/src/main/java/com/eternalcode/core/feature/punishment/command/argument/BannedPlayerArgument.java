package com.eternalcode.core.feature.punishment.command.argument;

import com.eternalcode.core.feature.punishment.KnownPlayerResolver;
import com.eternalcode.core.feature.punishment.ban.BanService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.TranslationManager;

import org.bukkit.OfflinePlayer;

@LiteArgument(type = OfflinePlayer.class, name = BannedPlayerArgument.KEY)
public class BannedPlayerArgument extends PunishedPlayerArgument {

    public static final String KEY = "bannedPlayer";

    @Inject
    public BannedPlayerArgument(TranslationManager translationManager, KnownPlayerResolver knownPlayerResolver, BanService banService) {
        super(translationManager, knownPlayerResolver, banService::activeBans);
    }
}
