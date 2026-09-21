package com.eternalcode.core.feature.enderchest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class Enderchest {

    private static final int[] NO_SLOTS = new int[0];

    private final UUID ownerUniqueId;
    private final EnderchestLayout layout;
    private final Set<Integer> dirtyPages = new HashSet<>();

    private String ownerName;
    private UUID viewerUniqueId;
    private ItemStack[] slots;
    private boolean rewriteRequired;
    private int viewers;
    private CompletableFuture<Void> pendingWrite = CompletableFuture.completedFuture(null);

    Enderchest(UUID ownerUniqueId, String ownerName, EnderchestLayout layout, ItemStack[] slots, boolean rewriteRequired) {
        this.ownerUniqueId = ownerUniqueId;
        this.ownerName = ownerName;
        this.layout = layout;
        this.slots = slots;
        this.rewriteRequired = rewriteRequired;
    }

    static Enderchest fromPages(UUID ownerUniqueId, String ownerName, EnderchestLayout layout, List<PageContents> pages) {
        List<ItemStack> flatSlots = new ArrayList<>();
        boolean rewriteRequired = false;
        int expectedPage = EnderchestLayout.FIRST_PAGE;

        for (PageContents page : pages) {
            while (expectedPage < page.page()) {
                addEmptySlots(flatSlots, layout.capacityOf(expectedPage));
                expectedPage++;
            }

            ItemStack[] items = page.items();
            if (items.length != layout.capacityOf(page.page())) {
                rewriteRequired = true;
            }

            for (ItemStack item : items) {
                flatSlots.add(nullIfEmpty(item));
            }
            expectedPage++;
        }

        addEmptySlots(flatSlots, layout.totalSlots(layout.pagesIn(flatSlots.size())) - flatSlots.size());
        return new Enderchest(ownerUniqueId, ownerName, layout, flatSlots.toArray(new ItemStack[0]), rewriteRequired);
    }

    UUID getOwnerUniqueId() {
        return this.ownerUniqueId;
    }

    String getOwnerName() {
        return this.ownerName;
    }

    void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    EnderchestLayout getLayout() {
        return this.layout;
    }

    ItemStack[] getPageContents(int page) {
        int capacity = this.layout.capacityOf(page);
        int offset = this.layout.offsetOf(page);
        this.ensureSlots(offset + capacity);

        ItemStack[] contents = new ItemStack[capacity];
        for (int index = 0; index < capacity; index++) {
            contents[index] = copyItem(this.slots[offset + index]);
        }

        return contents;
    }

    void updatePage(int page, ItemStack[] contents) {
        int capacity = this.layout.capacityOf(page);
        int offset = this.layout.offsetOf(page);
        this.ensureSlots(offset + capacity);

        boolean changed = false;
        for (int index = 0; index < capacity; index++) {
            ItemStack item = index < contents.length ? nullIfEmpty(contents[index]) : null;

            if (Objects.equals(this.slots[offset + index], item)) {
                continue;
            }

            this.slots[offset + index] = copyItem(item);
            changed = true;
        }

        if (changed) {
            this.markDirty(page);
        }
    }

    int[] insertItems(ItemStack[] items) {
        int count = 0;
        for (ItemStack candidate : items) {
            if (nullIfEmpty(candidate) != null) {
                count++;
            }
        }

        if (count == 0) {
            return NO_SLOTS;
        }

        int[] filled = new int[count];
        int index = 0;

        for (ItemStack candidate : items) {
            ItemStack item = nullIfEmpty(candidate);
            if (item == null) {
                continue;
            }

            int slot = this.firstFreeSlot();
            this.slots[slot] = item.clone();
            this.markDirty(this.layout.pageOf(slot));
            filled[index++] = slot;
        }

        return filled;
    }

    boolean hasAlreadyImported(ItemStack[] items) {
        List<ItemStack> importedItems = new ArrayList<>();

        for (ItemStack candidate : items) {
            ItemStack item = nullIfEmpty(candidate);
            if (item != null) {
                importedItems.add(item);
            }
        }

        int tailStart = this.lastOccupiedSlot() - importedItems.size() + 1;
        if (importedItems.isEmpty() || tailStart < 0) {
            return false;
        }

        for (int index = 0; index < importedItems.size(); index++) {
            if (!importedItems.get(index).equals(this.slots[tailStart + index])) {
                return false;
            }
        }

        return true;
    }

    void clearSlots(int[] targets) {
        for (int slot : targets) {
            if (slot >= this.slots.length || this.slots[slot] == null) {
                continue;
            }

            this.slots[slot] = null;
            this.markDirty(this.layout.pageOf(slot));
        }
    }

    private int lastOccupiedSlot() {
        for (int slot = this.slots.length - 1; slot >= 0; slot--) {
            if (this.slots[slot] != null) {
                return slot;
            }
        }

        return -1;
    }

    int getUsedPages() {
        for (int slot = this.slots.length - 1; slot >= 0; slot--) {
            if (this.slots[slot] != null) {
                return this.layout.pageOf(slot);
            }
        }

        return 0;
    }

    EnderchestWrite prepareWrite() {
        if (this.hasNothingToWrite()) {
            return EnderchestWrite.NONE;
        }

        boolean replaceAll = this.rewriteRequired;
        int pageCount = this.layout.pagesIn(this.slots.length);
        List<PageContents> pages = new ArrayList<>();

        for (int page = 1; page <= pageCount; page++) {
            if (replaceAll || this.dirtyPages.contains(page)) {
                pages.add(new PageContents(page, this.getPageContents(page)));
            }
        }

        this.dirtyPages.clear();
        this.rewriteRequired = false;
        this.pendingWrite = new CompletableFuture<>();
        return new EnderchestWrite(pages, replaceAll);
    }

    void restoreDirtyPages(EnderchestWrite write) {
        this.rewriteRequired |= write.replaceAll();

        for (PageContents page : write.pages()) {
            this.dirtyPages.add(page.page());
        }
    }

    void attachViewer(UUID viewerUniqueId) {
        this.viewerUniqueId = viewerUniqueId;
        this.viewers++;
    }

    void detachViewer() {
        this.viewers = Math.max(0, this.viewers - 1);

        if (this.viewers == 0) {
            this.viewerUniqueId = null;
        }
    }

    boolean isViewedByOther(UUID viewerUniqueId) {
        return this.viewers > 0 && !viewerUniqueId.equals(this.viewerUniqueId);
    }

    boolean hasViewers() {
        return this.viewers > 0;
    }

    boolean hasNothingToWrite() {
        return !this.rewriteRequired && this.dirtyPages.isEmpty();
    }

    boolean isPersisted() {
        return !this.rewriteRequired && this.dirtyPages.isEmpty() && this.pendingWrite.isDone();
    }

    boolean isWriting() {
        return !this.pendingWrite.isDone();
    }

    void finishWrite() {
        this.pendingWrite.complete(null);
    }

    private void markDirty(int page) {
        this.dirtyPages.add(page);
    }

    private int firstFreeSlot() {
        for (int slot = 0; slot < this.slots.length; slot++) {
            if (this.slots[slot] == null) {
                return slot;
            }
        }

        int slot = this.slots.length;
        this.ensureSlots(slot + 1);
        return slot;
    }

    private void ensureSlots(int required) {
        if (this.slots.length >= required) {
            return;
        }

        ItemStack[] grownSlots = new ItemStack[this.layout.totalSlots(this.layout.pagesIn(required))];
        System.arraycopy(this.slots, 0, grownSlots, 0, this.slots.length);
        this.slots = grownSlots;
    }

    private static void addEmptySlots(List<ItemStack> target, int amount) {
        for (int index = 0; index < amount; index++) {
            target.add(null);
        }
    }

    private static ItemStack nullIfEmpty(ItemStack item) {
        if (item == null || item.getAmount() <= 0) {
            return null;
        }

        Material type = item.getType();
        return type == Material.AIR || type == Material.CAVE_AIR || type == Material.VOID_AIR ? null : item;
    }

    private static ItemStack copyItem(ItemStack item) {
        return item == null ? null : item.clone();
    }
}
