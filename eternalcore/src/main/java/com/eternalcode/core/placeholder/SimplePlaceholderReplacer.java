package com.eternalcode.core.placeholder;

import java.util.function.Supplier;

class SimplePlaceholderReplacer implements PlaceholderReplacer {

    private final String target;
    private final Supplier<String> replacement;

    SimplePlaceholderReplacer(String target, Supplier<String> replacement) {
        this.target = target;
        this.replacement = replacement;
    }

    @Override
    public String apply(String text) {
        return text.replace(target, replacement.get());
    }

}
