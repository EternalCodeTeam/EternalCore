package com.eternalcode.core.feature.punishment.gui;

import static com.eternalcode.core.feature.punishment.gui.Menu.SLOTS_PER_ROW;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class PunishmentHistoryGuiLayout {

    static final int MIN_CONTENT_ROWS = 1;
    static final int MAX_CONTENT_ROWS = 4;

    private static final int BORDER_ROWS = 2;
    private static final int TOP_ROW = 0;
    private static final int FIRST_CONTENT_ROW = 1;
    private static final int SINGLE_ROW = 1;
    private static final int PREVIOUS_PAGE_COLUMN = 3;
    private static final int FILTER_COLUMN = 4;
    private static final int NEXT_PAGE_COLUMN = 5;

    private final int contentRows;
    private final List<Integer> contentSlots;
    private final List<Integer> borderSlots;

    PunishmentHistoryGuiLayout(int contentRows) {
        if (contentRows < MIN_CONTENT_ROWS || contentRows > MAX_CONTENT_ROWS) {
            throw new IllegalArgumentException(
                "contentRows must be between " + MIN_CONTENT_ROWS + " and " + MAX_CONTENT_ROWS + ", got " + contentRows);
        }

        this.contentRows = contentRows;
        this.contentSlots = slotsInRows(FIRST_CONTENT_ROW, contentRows);
        this.borderSlots = Stream.concat(
            slotsInRows(TOP_ROW, SINGLE_ROW).stream(),
            slotsInRows(this.bottomRow(), SINGLE_ROW).stream()
        ).toList();
    }

    int rows() {
        return this.contentRows + BORDER_ROWS;
    }

    int pageSize() {
        return this.contentSlots.size();
    }

    List<Integer> contentSlots() {
        return this.contentSlots;
    }

    List<Integer> borderSlots() {
        return this.borderSlots;
    }

    int previousPageSlot() {
        return this.bottomRowSlot(PREVIOUS_PAGE_COLUMN);
    }

    int filterSlot() {
        return this.bottomRowSlot(FILTER_COLUMN);
    }

    int nextPageSlot() {
        return this.bottomRowSlot(NEXT_PAGE_COLUMN);
    }

    private int bottomRowSlot(int column) {
        return this.bottomRow() * SLOTS_PER_ROW + column;
    }

    private int bottomRow() {
        return this.rows() - 1;
    }

    private static List<Integer> slotsInRows(int firstRow, int rowCount) {
        return IntStream.range(firstRow * SLOTS_PER_ROW, (firstRow + rowCount) * SLOTS_PER_ROW)
            .boxed()
            .toList();
    }
}
