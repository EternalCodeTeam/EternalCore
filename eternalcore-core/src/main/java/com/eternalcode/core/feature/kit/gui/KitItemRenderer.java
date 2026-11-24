package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.multification.shared.Formatter;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@Service
class KitItemRenderer {

    private final MiniMessage miniMessage;

    @Inject
    KitItemRenderer(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    ItemStack renderKitIcon(Kit kit, List<String> loreLines, Formatter formatter) {
        List<Component> lore = new ArrayList<>();

        for (String line : kit.description()) {
            lore.add(this.deserialize(line));
        }

        for (String line : loreLines) {
            lore.add(this.deserialize(formatter.format(line)));
        }

        return ItemBuilder.from(kit.icon())
            .name(this.deserialize(kit.displayName()))
            .lore(lore)
            .flags(ItemFlag.values())
            .build();
    }

    ItemStack renderButton(Material material, String name, List<String> lore) {
        return ItemBuilder.from(material)
            .name(this.deserialize(name))
            .lore(lore.stream().map(this::deserialize).toList())
            .flags(ItemFlag.values())
            .build();
    }

    ItemStack renderFiller(Material material) {
        return ItemBuilder.from(material)
            .name(Component.text(" "))
            .flags(ItemFlag.values())
            .build();
    }

    Component deserialize(String text) {
        return AdventureUtil.resetItalic(this.miniMessage.deserialize(text));
    }

    String deserializeLegacy(String text) {
        return AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(text));
    }
}
