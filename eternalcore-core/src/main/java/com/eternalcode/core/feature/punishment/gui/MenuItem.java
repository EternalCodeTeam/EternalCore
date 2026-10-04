package com.eternalcode.core.feature.punishment.gui;

import java.util.List;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public record MenuItem(
    Material material,
    Component name,
    List<Component> lore,
    boolean glowing,
    Consumer<Player> clickHandler
) {

    private static final Consumer<Player> NO_ACTION = viewer -> {};

    public MenuItem {
        lore = List.copyOf(lore);
    }

    public static MenuItem display(Material material, Component name, List<Component> lore) {
        return display(material, name, lore, false);
    }

    public static MenuItem display(Material material, Component name, List<Component> lore, boolean glowing) {
        return new MenuItem(material, name, lore, glowing, NO_ACTION);
    }

    public static MenuItem clickable(Material material, Component name, List<Component> lore, Consumer<Player> clickHandler) {
        return new MenuItem(material, name, lore, false, clickHandler);
    }

    public void click(Player viewer) {
        this.clickHandler.accept(viewer);
    }
}
