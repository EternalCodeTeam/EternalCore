package com.eternalcode.core.placeholder;

import panda.std.Lazy;

import java.util.function.Supplier;

public interface PlaceholderReplacer {

    String apply(String text);

    static PlaceholderReplacer of(String target, String replacement) {
        return new SimplePlaceholderReplacer(target, () -> replacement);
    }

    static PlaceholderReplacer of(String target, Supplier<String> replacement) {
        return new SimplePlaceholderReplacer(target, replacement);
    }

    static PlaceholderReplacer ofLazy(String target, Lazy<String> replacement) {
        return new SimplePlaceholderReplacer(target, replacement);
    }

}
