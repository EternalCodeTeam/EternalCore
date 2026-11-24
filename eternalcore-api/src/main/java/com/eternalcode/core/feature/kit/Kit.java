package com.eternalcode.core.feature.kit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Immutable kit definition, stored in {@code plugins/EternalCore/kits/<kit name>.yml}.
 * Every {@link ItemStack} going in or out is defensively cloned, so mutating returned items never changes the kit.
 * Use {@link #toBuilder()} to derive a modified copy.
 *
 * @param name         unique kit name, see {@link #isValidName(String)}.
 * @param displayName  MiniMessage formatted display name.
 * @param description  MiniMessage formatted description lines, shown in the GUI and in {@code /kit info}.
 * @param type         how often the kit can be claimed, see {@link KitType}.
 * @param cooldown     time between claims, used only by {@link KitType#RECURRING} kits.
 * @param permission   permission required to claim the kit.
 * @param requirements additional conditions checked before claiming.
 * @param items        items given on claim.
 * @param commands     console commands executed after a claim, {@code {PLAYER}} is replaced with the player name.
 * @param icon         GUI icon.
 * @param slot         GUI slot.
 */
public record Kit(
    String name,
    String displayName,
    List<String> description,
    KitType type,
    Duration cooldown,
    String permission,
    KitRequirements requirements,
    List<ItemStack> items,
    List<String> commands,
    ItemStack icon,
    int slot
) {

    public static final int MIN_SLOT = 0;

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_-]{1,32}$");

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

        if (isEmpty(icon)) {
            throw new IllegalArgumentException("icon cannot be empty");
        }

        if (slot < MIN_SLOT) {
            throw new IllegalArgumentException("slot cannot be lower than " + MIN_SLOT);
        }

        displayName = displayName == null ? name : displayName;
        description = List.copyOf(description);
        items = cloneItems(items);
        commands = List.copyOf(commands);
        icon = icon.clone();
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    /**
     * @return {@code true} for {@code null}, air and zero-amount items. Works on Paper and Spigot.
     */
    static boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR || item.getAmount() <= 0;
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public Builder toBuilder() {
        return new Builder(this.name)
            .displayName(this.displayName)
            .description(this.description)
            .type(this.type)
            .cooldown(this.cooldown)
            .permission(this.permission)
            .requirements(this.requirements)
            .items(this.items)
            .commands(this.commands)
            .icon(this.icon)
            .slot(this.slot);
    }

    /**
     * @return {@code true} if the kit is {@link KitType#RECURRING} and has a non-zero cooldown.
     */
    public boolean hasCooldown() {
        return this.type == KitType.RECURRING && !this.cooldown.isZero();
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
            if (!isEmpty(item)) {
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
        return "Kit{name='" + this.name + "', type=" + this.type + ", cooldown=" + this.cooldown + ", permission='" + this.permission + "'}";
    }

    public static final class Builder {

        private final String name;
        private String displayName;
        private List<String> description = List.of();
        private KitType type = KitType.RECURRING;
        private Duration cooldown = Duration.ZERO;
        private String permission;
        private KitRequirements requirements = KitRequirements.none();
        private List<ItemStack> items = List.of();
        private List<String> commands = List.of();
        private ItemStack icon;
        private int slot = MIN_SLOT;

        private Builder(String name) {
            this.name = name;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder description(Collection<String> description) {
            this.description = List.copyOf(description);
            return this;
        }

        public Builder type(KitType type) {
            this.type = type;
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

        public Builder requirements(KitRequirements requirements) {
            this.requirements = requirements;
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
         * Without an explicit icon the kit uses a chest.
         */
        public Kit build() {
            return new Kit(
                this.name,
                this.displayName,
                this.description,
                this.type,
                this.cooldown,
                this.permission,
                this.requirements,
                this.items,
                this.commands,
                this.icon == null ? new ItemStack(Material.CHEST) : this.icon,
                this.slot
            );
        }
    }
}
