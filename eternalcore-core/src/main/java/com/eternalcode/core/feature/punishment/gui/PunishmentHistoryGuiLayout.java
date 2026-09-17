package com.eternalcode.core.feature.punishment.gui;

final class PunishmentHistoryGuiLayout {

    private static final int SLOTS_PER_ROW = 9;
    private static final int MIN_CONTENT_ROWS = 1;
    private static final int MAX_CONTENT_ROWS = 4;

    private PunishmentHistoryGuiLayout() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static String[] buildStructure(int configuredPageSize) {
        int contentRows = clampRows(configuredPageSize);
        String[] structure = new String[contentRows + 2];

        structure[0] = "# # # # # # # # #";

        for (int row = 1; row <= contentRows; row++) {
            structure[row] = "x x x x x x x x x";
        }

        structure[contentRows + 1] = "# # # < # > # # #";

        return structure;
    }

    static int effectivePageSize(int configuredPageSize) {
        return clampRows(configuredPageSize) * SLOTS_PER_ROW;
    }

    private static int clampRows(int configuredPageSize) {
        int rows = (int) Math.ceil(configuredPageSize / (double) SLOTS_PER_ROW);
        return Math.max(MIN_CONTENT_ROWS, Math.min(MAX_CONTENT_ROWS, rows));
    }
}
