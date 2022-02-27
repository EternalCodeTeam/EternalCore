package com.eternalcode.core.chat.audience;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BukkitAudienceProvider implements AudienceProvider {

    private final UserManager userManager;
    private final Server server;
    private final net.kyori.adventure.platform.AudienceProvider audienceProvider;

    public BukkitAudienceProvider(UserManager userManager, Server server, net.kyori.adventure.platform.AudienceProvider audienceProvider) {
        this.userManager = userManager;
        this.server = server;
        this.audienceProvider = audienceProvider;
    }

    @Override
    public Collection<Audience> all() {
        Collection<Audience> audiences = this.allPlayers();

        audiences.add(console());

        return audiences;
    }

    @Override
    public Collection<Audience> allPlayers() {
        Set<Audience> audiences = new HashSet<>();

        for (Player player : server.getOnlinePlayers()) {
            audiences.add(this.player(player.getUniqueId()));
        }

        return audiences;
    }

    @Override
    public Audience console() {
        return new Audience(this.audienceProvider.console(), Language.DEFAULT);
    }

    @Override
    public Audience player(UUID uuid) {
        Language language = userManager.getUser(uuid)
            .map(user -> user.getSettings().getLanguage())
            .orElseGet(Language.DEFAULT);

        return new Audience(this.audienceProvider.player(uuid), language);
    }

    @Override
    public Audience user(User user) {
        return new Audience(this.audienceProvider.player(user.getUuid()), user.getSettings().getLanguage());
    }

}
