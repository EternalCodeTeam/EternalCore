package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitConfig;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Builds display items. The only kit GUI class that knows how items are decorated.
 */
@Service
class KitItemRenderer {

    private static final Component EMPTY_NAME = Component.text(" ");

    private final MiniMessage miniMessage;

    @Inject
    KitItemRenderer(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    ItemStack renderKitIcon(Kit kit, List<String> statusLore, Map<String, String> placeholders) {
        ItemStack icon = kit.icon();
        List<Component> lore = new ArrayList<>(this.readLegacyLore(icon));

        statusLore.stream()
            .map(line -> this.deserialize(this.applyPlaceholders(line, placeholders)))
            .forEach(lore::add);

        return ItemBuilder.from(icon)
            .name(this.deserialize(kit.displayName()))
            .lore(lore)
            .flags(ItemFlag.values())
            .build();
    }

    ItemStack renderButton(KitConfig.Button button) {
        return ItemBuilder.from(button.material())
            .name(this.deserialize(button.name()))
            .lore(button.lore().stream().map(this::deserialize).toList())
            .flags(ItemFlag.values())
            .build();
    }

    ItemStack renderFiller(Material material) {
        return ItemBuilder.from(material)
            .name(EMPTY_NAME)
            .flags(ItemFlag.values())
            .build();
    }

    Component deserialize(String text) {
        return AdventureUtil.resetItalic(this.miniMessage.deserialize(text));
    }

    String deserializeLegacy(String text) {
        return AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(text));
    }

    private List<Component> readLegacyLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null || !meta.hasLore() || meta.getLore() == null) {
            return List.of();
        }

        return meta.getLore().stream()
            .map(line -> (Component) AdventureUtil.SECTION_SERIALIZER.deserialize(line))
            .toList();
    }

    private String applyPlaceholders(String text, Map<String, String> placeholders) {
        String result = text;

        for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            result = result.replace(placeholder.getKey(), placeholder.getValue());
        }

        return result;
    }
}
