package com.eternalcode.core.bridge.placeholderapi;

import com.eternalcode.core.bridge.BridgeInitializer;
import com.eternalcode.core.placeholder.PlaceholderRaw;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PlaceholderApiExtension extends PlaceholderExpansion implements BridgeInitializer {

    private final PlaceholderRegistry placeholderRegistry;
    private final Plugin plugin;

    public PlaceholderApiExtension(PlaceholderRegistry placeholderRegistry, Plugin plugin) {
        this.placeholderRegistry = placeholderRegistry;
        this.plugin = plugin;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Optional<PlaceholderRaw> optional = this.placeholderRegistry.getRawPlaceholder(params);

        if (optional.isPresent()) {
            return optional.get().rawApply(player);
        }

        return "Unknown placeholder!";
    }

    @Override
    public @NotNull String getIdentifier() {
        return this.plugin.getDescription().getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return "EternalCodeTeam";
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public void initialize() {
        this.register();
    }

}
