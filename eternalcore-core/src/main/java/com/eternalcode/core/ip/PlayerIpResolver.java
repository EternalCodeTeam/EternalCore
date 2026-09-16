package com.eternalcode.core.ip;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class PlayerIpResolver {

    private final PlayerIpService playerIpService;

    @Inject
    PlayerIpResolver(PlayerIpService playerIpService) {
        this.playerIpService = Objects.requireNonNull(playerIpService, "playerIpService cannot be null");
    }

    public CompletableFuture<Optional<String>> resolve(OfflinePlayer target) {
        Objects.requireNonNull(target, "target cannot be null");

        if (target instanceof Player onlinePlayer && onlinePlayer.getAddress() != null && onlinePlayer.getAddress().getAddress() != null) {
            return CompletableFuture.completedFuture(Optional.of(onlinePlayer.getAddress().getAddress().getHostAddress()));
        }

        return this.playerIpService.findLastKnownIp(target.getUniqueId());
    }
}
