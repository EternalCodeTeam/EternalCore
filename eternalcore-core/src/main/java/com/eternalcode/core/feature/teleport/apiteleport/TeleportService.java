package com.eternalcode.core.feature.teleport.apiteleport;

import com.eternalcode.core.feature.teleport.config.TeleportMessages;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public interface TeleportService {

    /**
     * Teleportuje gracza natychmiastowo do podanej lokalizacji.
     *
     * @param player   gracz do przeteleportowania
     * @param location docelowa lokalizacja
     */
    void teleport(Player player, Location location);

    /**
     * Rozpoczyna proces teleportacji z opóźnieniem na podstawie uprawnień gracza.
     *
     * @param player   gracz do przeteleportowania
     * @param location docelowa lokalizacja
     * @return CompletableFuture z wynikiem teleportacji
     */
    CompletableFuture<TeleportResult> teleportWithDelay(Player player, Location location, TeleportMessages messages);

    /**
     * Rozpoczyna proces teleportacji z określonym opóźnieniem.
     *
     * @param player   gracz do przeteleportowania
     * @param location docelowa lokalizacja
     * @param delay    czas opóźnienia
     * @return CompletableFuture z wynikiem teleportacji
     */
    CompletableFuture<TeleportResult> teleportWithDelay(Player player, Location location, Duration delay, TeleportMessages messages);

    /**
     * Pobiera ostatnią lokalizację gracza.
     *
     * @param player UUID gracza
     * @return Optional z ostatnią lokalizacją lub pusty jeśli nie istnieje
     */
    Optional<Location> getLastLocation(UUID player);

    /**
     * Oznacza lokalizację jako ostatnią pozycję gracza.
     *
     * @param player   UUID gracza
     * @param location lokalizacja do zaznaczenia
     */
    void markLastLocation(UUID player, Location location);
}
