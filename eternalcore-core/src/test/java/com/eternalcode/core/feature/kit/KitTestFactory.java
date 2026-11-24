package com.eternalcode.core.feature.kit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class KitTestFactory {

    private KitTestFactory() {
    }

    public static Kit.Builder kit(String name) {
        return Kit.builder(name)
            .permission("eternalcore.kits." + name)
            .icon(item())
            .items(List.of(item()));
    }

    public static Kit recurring(String name, Duration cooldown) {
        return kit(name).type(KitType.RECURRING).cooldown(cooldown).build();
    }

    public static ItemStack item() {
        ItemStack item = mock(ItemStack.class);
        when(item.getType()).thenReturn(Material.STONE);
        when(item.getAmount()).thenReturn(1);
        when(item.clone()).thenReturn(item);
        return item;
    }
}
