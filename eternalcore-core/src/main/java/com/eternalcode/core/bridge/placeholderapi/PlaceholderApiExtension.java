package com.eternalcode.core.bridge.placeholderapi;

import com.eternalcode.core.bridge.BridgeInitializer;
import com.eternalcode.core.placeholder.PlaceholderRaw;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PlaceholderApiExtension extends PlaceholderExpansion implements BridgeInitializer {

    private final PlaceholderRegistry placeholderRegistry;
    private final PluginDescriptionFile pluginDescriptionFile;

    public PlaceholderApiExtension(PlaceholderRegistry placeholderRegistry, PluginDescriptionFile pluginDescriptionFile) {
        this.placeholderRegistry = placeholderRegistry;
        this.pluginDescriptionFile = pluginDescriptionFile;
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
        return "eternalcore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "EternalCodeTeam";
    }

    @Override
    public @NotNull String getVersion() {
        return this.pluginDescriptionFile.getVersion();
    }

    @Override
    public void initialize() {
        this.register();
    }

}
