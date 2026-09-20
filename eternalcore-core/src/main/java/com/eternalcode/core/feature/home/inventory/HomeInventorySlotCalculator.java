package com.eternalcode.core.feature.home.inventory;

import com.eternalcode.core.feature.home.Home;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class HomeInventorySlotCalculator {

    private HomeInventorySlotCalculator() {
    }

    public enum SlotState {
        OCCUPIED,
        AVAILABLE,
        LOCKED
    }

    public record HomeSlot(int index, SlotState state, Home home) {
    }

    public static List<HomeSlot> calculate(Collection<Home> homes, int limit, int globalMax) {
        List<Home> sortedHomes = homes.stream()
            .sorted(Comparator.comparing(Home::getName))
            .toList();

        List<HomeSlot> slots = new ArrayList<>(globalMax);

        for (int i = 1; i <= globalMax; i++) {
            if (i <= sortedHomes.size()) {
                slots.add(new HomeSlot(i, SlotState.OCCUPIED, sortedHomes.get(i - 1)));
            }
            else if (i <= limit) {
                slots.add(new HomeSlot(i, SlotState.AVAILABLE, null));
            }
            else {
                slots.add(new HomeSlot(i, SlotState.LOCKED, null));
            }
        }

        return slots;
    }

    public static int globalMaxSlots(Map<String, Integer> maxHomes, int defaultLimit) {
        int highestPermissionLimit = maxHomes.values().stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(defaultLimit);

        return Math.max(highestPermissionLimit, defaultLimit);
    }
}
