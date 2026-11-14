package com.eternalcode.core.feature.kit.gui;

public final class KitGuiLayout {

    public static final int ROW_SIZE = 9;
    public static final int MIN_ROWS = 1;
    public static final int MAX_ROWS = 6;

    private KitGuiLayout() {
    }

    public static int clampRows(int rows) {
        return Math.max(MIN_ROWS, Math.min(MAX_ROWS, rows));
    }

    public static int slotCount(int rows) {
        return clampRows(rows) * ROW_SIZE;
    }

    public static int rowsFor(int itemCount) {
        return clampRows((itemCount + ROW_SIZE - 1) / ROW_SIZE);
    }

    public static int lastRowSlot(int rows, int column) {
        int safeColumn = Math.max(0, Math.min(ROW_SIZE - 1, column));
        return (clampRows(rows) - 1) * ROW_SIZE + safeColumn;
    }
}
