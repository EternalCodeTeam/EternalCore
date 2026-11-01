package com.eternalcode.core.feature.kit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Immutable kit definition. Every {@link ItemStack} going in or out is defensively cloned,
 * so mutating returned items never changes the kit. Use {@link #toBuilder()} to derive a modified copy.
 *
 * @param name        unique kit name, see {@link #isValidName(String)}.
 * @param displayName MiniMessage formatted display name.
 */
public record Kit(
    String name,
    String displayName,
    Duration cooldown,
    String permission,
    List<ItemStack> items,
    List<String> commands,
    ItemStack icon,
    int slot
) {

    public static final int MIN_SLOT = 0;

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_-]{1,32}$");
    private static final Material DEFAULT_ICON = Material.CHEST;

    public Kit {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid kit name '" + name + "', expected " + NAME_PATTERN.pattern());
        }

        if (cooldown.isNegative()) {
            throw new IllegalArgumentException("cooldown cannot be negative");
        }

        if (permission.isBlank()) {
            throw new IllegalArgumentException("permission cannot be blank");
        }

        if (icon.getType().isAir()) {
            throw new IllegalArgumentException("icon cannot be air");
        }

        if (slot < MIN_SLOT) {
            throw new IllegalArgumentException("slot cannot be lower than " + MIN_SLOT);
        }

        displayName = displayName == null ? name : displayName;
        items = cloneItems(items);
        commands = List.copyOf(commands);
        icon = icon.clone();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public Builder toBuilder() {
        return new Builder(this.name)
            .displayName(this.displayName)
            .cooldown(this.cooldown)
            .permission(this.permission)
            .items(this.items)
            .commands(this.commands)
            .icon(this.icon)
            .slot(this.slot);
    }

    public boolean hasCooldown() {
        return !this.cooldown.isZero();
    }

    /**
     * @return defensive copy - ItemStack is mutable.
     */
    @Override
    public List<ItemStack> items() {
        return cloneItems(this.items);
    }

    /**
     * @return defensive copy - ItemStack is mutable.
     */
    @Override
    public ItemStack icon() {
        return this.icon.clone();
    }

    private static List<ItemStack> cloneItems(Collection<ItemStack> source) {
        List<ItemStack> copy = new ArrayList<>(source.size());

        for (ItemStack item : source) {
            if (item != null && !item.getType().isAir()) {
                copy.add(item.clone());
            }
        }

        return List.copyOf(copy);
    }

    /**
     * Kits are identified by name only.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Kit kit)) {
            return false;
        }

        return this.name.equals(kit.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return "Kit{name='" + this.name + "', cooldown=" + this.cooldown + ", permission='" + this.permission + "'}";
    }

    public static final class Builder {

        private final String name;
        private String displayName;
        private Duration cooldown = Duration.ZERO;
        private String permission;
        private List<ItemStack> items = List.of();
        private List<String> commands = List.of();
        private ItemStack icon = new ItemStack(DEFAULT_ICON);
        private int slot = MIN_SLOT;

        private Builder(String name) {
            this.name = name;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder cooldown(Duration cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public Builder permission(String permission) {
            this.permission = permission;
            return this;
        }

        public Builder items(Collection<ItemStack> items) {
            this.items = new ArrayList<>(items);
            return this;
        }

        public Builder commands(Collection<String> commands) {
            this.commands = List.copyOf(commands);
            return this;
        }

        public Builder icon(ItemStack icon) {
            this.icon = icon;
            return this;
        }

        public Builder slot(int slot) {
            this.slot = slot;
            return this;
        }

        /**
         * Validation and defensive copying happen in the record's compact constructor.
         */
        public Kit build() {
            return new Kit(
                this.name,
                this.displayName,
                this.cooldown,
                this.permission,
                this.items,
                this.commands,
                this.icon,
                this.slot
            );
        }
    }
}
