package com.eternalcode.core.feature.warp.repository;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.feature.warp.messages.WarpMessages;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.Material;

/**
 * Utility class to help migrate from the old warp configuration to the new one.
 * This class is used to migrate warp locations and GUI settings to the new warps.yml file.
 */
public class WarpMigrationUtil {

    /**
     * Migrates warp locations and GUI settings to the new warps.yml file.
     * 
     * @param translationManager The translation manager
     * @param warpConfig The new warp configuration
     * @param configurationManager The configuration manager
     * @param locationsConfiguration The old locations configuration
     * @return true if migration was successful, false otherwise
     */
    public static boolean migrateWarps(
        TranslationManager translationManager,
        WarpConfig warpConfig,
        ConfigurationManager configurationManager,
        LocationsConfiguration locationsConfiguration
    ) {
        boolean migrated = false;
        
        // Migrate warp locations from old configuration
        if (locationsConfiguration != null && !locationsConfiguration.warps.isEmpty()) {
            warpConfig.warps.putAll(locationsConfiguration.warps
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                    entry -> entry.getKey(),
                    entry -> new WarpConfig.WarpConfigEntry(entry.getValue(), new ArrayList<>()))
                )
            );

            locationsConfiguration.warps.clear();
            configurationManager.save(locationsConfiguration);
            migrated = true;
        }
        
        // Migrate GUI settings from language files
        boolean guiMigrated = migrateGuiSettings(
            translationManager,
            warpConfig,
            configurationManager
        );
        
        migrated |= guiMigrated;
        
        // Save the updated warp configuration
        if (migrated) {
            configurationManager.save(warpConfig);
        }
        
        return migrated;
    }

    /**
     * Migrates warp GUI settings from language files to the new warps.yml file.
     * 
     * @param translationManager The translation manager
     * @param warpConfig The new warp configuration
     * @param configurationManager The configuration manager
     * @return true if migration was successful, false otherwise
     */
    public static boolean migrateGuiSettings(
        TranslationManager translationManager,
        WarpConfig warpConfig,
        ConfigurationManager configurationManager
    ) {
        // Get the first available language to use as a base for GUI settings
        Translation translation = translationManager.getAvailableLanguages().stream()
            .findFirst()
            .map(translationManager::getMessages)
            .orElse(null);
            
        if (translation == null) {
            return false;
        }
        
        WarpMessages.WarpInventorySection warpSection = translation.warp().warpInventory();
        
        // Migrate GUI settings
        WarpConfig.WarpGuiSettings guiSettings = warpConfig.guiSettings;
        guiSettings.title = warpSection.title();
        guiSettings.borderEnabled = warpSection.border().enabled();
        guiSettings.borderMaterial = Material.valueOf(warpSection.border().material().name());
        guiSettings.borderFillType = convertFillType(warpSection.border().fillType());
        guiSettings.borderName = warpSection.border().name();
        guiSettings.borderLore = warpSection.border().lore();
        guiSettings.decorationItems = warpSection.decorationItems().items();
        
        // Migrate warp items
        Map<String, WarpConfig.WarpConfigEntry> warps = new HashMap<>(warpConfig.warps);
        final boolean[] itemsMigrated = {false};
        
        warpSection.items().forEach((name, item) -> {
            WarpConfig.WarpConfigEntry warpEntry = warps.get(name);
            if (warpEntry == null) {
                return;
            }
            
            ConfigItem configItem = item.warpItem();
            WarpConfig.WarpGuiItem guiItem = warpEntry.guiItem;
            
            guiItem.name = configItem.name();
            guiItem.lore = configItem.lore();
            guiItem.material = configItem.material();
            guiItem.texture = configItem.texture();
            guiItem.slot = configItem.slot();
            guiItem.glow = configItem.glow();
            
            itemsMigrated[0] = true;
        });
        
        warpConfig.warps = warps;
        
        return true;
    }
    
    private static WarpConfig.WarpGuiSettings.BorderFillType convertFillType(WarpMessages.WarpInventorySection.BorderSection.FillType fillType) {
        return switch (fillType) {
            case TOP -> WarpConfig.WarpGuiSettings.BorderFillType.TOP;
            case BOTTOM -> WarpConfig.WarpGuiSettings.BorderFillType.BOTTOM;
            case BORDER -> WarpConfig.WarpGuiSettings.BorderFillType.BORDER;
            case ALL -> WarpConfig.WarpGuiSettings.BorderFillType.ALL;
        };
    }
} 
