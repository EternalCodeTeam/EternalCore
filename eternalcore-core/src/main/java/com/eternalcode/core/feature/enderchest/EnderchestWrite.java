package com.eternalcode.core.feature.enderchest;

import java.util.List;

public record EnderchestWrite(List<PageContents> pages, boolean replaceAll) {

    public static final EnderchestWrite NONE = new EnderchestWrite(List.of(), false);

    public boolean isEmpty() {
        return this.pages.isEmpty() && !this.replaceAll;
    }
}
