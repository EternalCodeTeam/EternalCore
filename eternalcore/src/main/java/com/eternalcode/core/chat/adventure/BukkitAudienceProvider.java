package com.eternalcode.core.chat.adventure;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.AudienceProvider;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BukkitAudienceProvider implements AudienceProvider {

    private final UserManager userManager;
    private final Server server;

    public BukkitAudienceProvider(UserManager userManager, Server server, net.kyori.adventure.platform.AudienceProvider audienceProvider) {
        this.userManager = userManager;
        this.server = server;
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
        return Audience.console();
    }

    @Override
    public Audience player(UUID uuid) {
        return Audience.player(uuid, userManager.getUser(uuid)
            .map(user -> user.getSettings().getLanguage())
            .orElseGet(Language.DEFAULT));
    }

    @Override
    public Audience user(User user) {
        return Audience.player(user.getUniqueId(), user.getSettings().getLanguage());
    }

}
