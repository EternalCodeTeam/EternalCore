package com.eternalcode.core.feature.punishment.gui;

import java.util.HashMap;
import java.util.Map;
import net.kyori.adventure.text.Component;

public final class Menu {

    public static final int SLOTS_PER_ROW = 9;
    public static final int MIN_ROWS = 1;
    public static final int MAX_ROWS = 6;

    private final Component title;
    private final int rows;
    private final Map<Integer, MenuItem> items;

    private Menu(Builder builder) {
        this.title = builder.title;
        this.rows = builder.rows;
        this.items = Map.copyOf(builder.items);
    }

    public Component title() {
        return this.title;
    }

    public int rows() {
        return this.rows;
    }

    public Map<Integer, MenuItem> items() {
        return this.items;
    }

    public static Builder builder(Component title, int rows) {
        return new Builder(title, rows);
    }

    public static final class Builder {

        private final Component title;
        private final int rows;
        private final Map<Integer, MenuItem> items = new HashMap<>();

        private Builder(Component title, int rows) {
            this.title = title;

            if (rows < MIN_ROWS || rows > MAX_ROWS) {
                throw new IllegalArgumentException("rows must be between " + MIN_ROWS + " and " + MAX_ROWS + ", got " + rows);
            }

            this.rows = rows;
        }

        public Builder item(int slot, MenuItem item) {

            int size = this.rows * SLOTS_PER_ROW;
            if (slot < 0 || slot >= size) {
                throw new IllegalArgumentException("slot must be between 0 and " + (size - 1) + ", got " + slot);
            }

            this.items.put(slot, item);
            return this;
        }

        public Builder items(Iterable<Integer> slots, MenuItem item) {
            for (int slot : slots) {
                this.item(slot, item);
            }

            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}
