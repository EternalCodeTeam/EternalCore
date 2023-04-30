package com.eternalcode.core.viewer;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.Server;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class BukkitViewerProvider implements ViewerProvider {

    private final UserManager userManager;
    private final Server server;

    public BukkitViewerProvider(UserManager userManager, Server server) {
        this.userManager = userManager;
        this.server = server;
    }

    @Override
    public Collection<Viewer> all() {
        Collection<Viewer> audiences = this.onlinePlayers();

        audiences.add(this.console());

        return audiences;
    }

    @Override
    public Collection<Viewer> onlinePlayers() {

        Set<Viewer> audiences = new HashSet<>();

        for (Player player : this.server.getOnlinePlayers()) {
            audiences.add(this.player(player.getUniqueId()));
        }

        return audiences;
    }

    @Override
    public Viewer console() {
        return BukkitViewerImpl.console();
    }

    @Override
    public Viewer player(UUID uuid) {
        return this.userManager.getUser(uuid)
            .map(Viewer.class::cast)
            .orElseGet(() -> BukkitViewerImpl.player(uuid, Language.DEFAULT));
    }

    @Override
    public Viewer user(User user) {
        return BukkitViewerImpl.player(user.getUniqueId(), user.getSettings().getLanguage());
    }

    public Viewer sender(CommandSender commandSender) {
        return this.any(commandSender);
    }

    public Viewer any(Object any) {
        if (any instanceof Player player) {
            Optional<User> userOption = this.userManager.getUser(player.getUniqueId());

            if (userOption.isPresent()) {
                return userOption.get();
            }

            return BukkitViewerImpl.player(player.getUniqueId(), Language.DEFAULT);
        }

        if (any instanceof ConsoleCommandSender || any instanceof RemoteConsoleCommandSender || any instanceof BlockCommandSender) {
            return BukkitViewerImpl.console();
        }

        throw new IllegalArgumentException("Unsupported sender type: " + any.getClass().getName());
    }

}
