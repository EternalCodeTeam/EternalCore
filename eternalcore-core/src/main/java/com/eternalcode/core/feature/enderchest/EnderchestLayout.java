package com.eternalcode.core.feature.enderchest;

record EnderchestLayout(int rows) {

    static final int FIRST_PAGE = 1;
    static final int MIN_ROWS = 1;
    static final int MAX_ROWS = 6;

    private static final int SLOTS_PER_ROW = 9;

    EnderchestLayout {
        if (rows < MIN_ROWS || rows > MAX_ROWS) {
            throw new IllegalArgumentException("Rows must be between " + MIN_ROWS + " and " + MAX_ROWS + ", got " + rows);
        }
    }

    static EnderchestLayout ofRows(int rows) {
        return new EnderchestLayout(Math.clamp(rows, MIN_ROWS, MAX_ROWS));
    }

    int inventorySize() {
        return this.rows * SLOTS_PER_ROW;
    }

    int nextPageSlot() {
        return this.inventorySize() - 1;
    }

    int previousPageSlot() {
        return this.inventorySize() - SLOTS_PER_ROW;
    }

    int capacityOf(int page) {
        return this.isFirstPage(page) ? this.inventorySize() - 1 : this.inventorySize() - 2;
    }

    int offsetOf(int page) {
        if (page < FIRST_PAGE) {
            throw new IllegalArgumentException("Page must be positive, got " + page);
        }

        return this.totalSlots(page - 1);
    }

    int totalSlots(int pages) {
        if (pages <= 0) {
            return 0;
        }

        return this.capacityOf(FIRST_PAGE) + (pages - 1) * this.capacityOf(FIRST_PAGE + 1);
    }

    int pageOf(int slot) {
        int firstPageCapacity = this.capacityOf(FIRST_PAGE);

        if (slot < firstPageCapacity) {
            return FIRST_PAGE;
        }

        return (slot - firstPageCapacity) / this.capacityOf(FIRST_PAGE + 1) + FIRST_PAGE + 1;
    }

    int pagesIn(int slots) {
        return slots <= 0 ? 0 : this.pageOf(slots - 1);
    }

    boolean isNavigationSlot(int page, int slot) {
        if (slot == this.nextPageSlot()) {
            return true;
        }

        return !this.isFirstPage(page) && slot == this.previousPageSlot();
    }

    int storageSlot(int page, int index) {
        if (this.isFirstPage(page) || index < this.previousPageSlot()) {
            return index;
        }

        return index + 1;
    }

    private boolean isFirstPage(int page) {
        return page == FIRST_PAGE;
    }
}
