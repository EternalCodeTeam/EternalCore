package com.eternalcode.core.feature.kit.repository;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.feature.kit.InventoryData;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

@ConfigurationFile
public class KitRepositoryConfig extends AbstractConfigurationFile {

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "data" + File.separator + "kits.yml");
    }

    @Comment({ "# Kits data", "# These are kits, for your own safety, please don't touch it." })
    public Map<String, KitConfigEntry> kits = new HashMap<>();

    public static class KitConfigEntry extends OkaeriConfig {

        public String name;
        public String permission;
        public Duration cooldown;
        public Map<Integer, ItemStack> items = new HashMap<>();
        public ItemStack displayItem;

        public KitConfigEntry() {
        }

        public KitConfigEntry(String name, String permission, Duration cooldown, InventoryData inventory, ItemStack displayItem) {
            this.name = name;
            this.permission = permission;
            this.cooldown = cooldown;
            this.items = inventory.items != null ? inventory.items : new HashMap<>();
            this.displayItem = displayItem;
        }

        public Kit toKit() {
            InventoryData inventoryData = new InventoryData(this.items);
            return new Kit(this.name, this.permission, this.cooldown, inventoryData, this.displayItem);
        }
    }
}