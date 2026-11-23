package com.eternalcode.core.feature.kit;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
public class KitConfig extends OkaeriConfig implements KitSettings {

    @Comment({"# ", "# Default "})
    public ConfigItem defaultDisplayItem = ConfigItem.builder()
        .withName("<color:#9d6eef>{KIT}")
        .withLore(List.of("<gray>Default kit description", "", "<white>Click to receive!"))
        .withMaterial(Material.CHEST)
        .withSlot(10)
        .build();

}
