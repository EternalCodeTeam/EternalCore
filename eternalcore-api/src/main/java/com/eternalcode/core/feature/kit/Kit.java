package com.eternalcode.core.feature.kit;

import java.time.Duration;
import org.bukkit.inventory.ItemStack;

public record Kit(
    String name,
    String permission,
    Duration duration,
    InventoryData inventory,
    ItemStack displayItem,
    int priority
) {

}
