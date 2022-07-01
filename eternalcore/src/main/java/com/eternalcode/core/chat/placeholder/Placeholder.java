package com.eternalcode.core.chat.placeholder;

import panda.utilities.text.Formatter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Placeholder<CONTEXT> {

    private final Map<String, Function<CONTEXT, String>> placeholders;

    private Placeholder(Map<String, Function<CONTEXT, String>> placeholders) {
        this.placeholders = placeholders;
    }

    public String format(String text, CONTEXT context) {
        for (Map.Entry<String, Function<CONTEXT, String>> entry : this.placeholders.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue().apply(context));
        }

        return text;
    }

    public Formatter toFormatter(CONTEXT context) {
        Formatter formatter = new Formatter();

        for (Map.Entry<String, Function<CONTEXT, String>> entry : this.placeholders.entrySet()) {
            formatter.register(entry.getKey(), entry.getValue().apply(context));
        }

        return formatter;
    }

    public static <CONTEXT> Builder<CONTEXT> builder() {
        return new Builder<>();
    }

    public static <CONTEXT> Placeholder<CONTEXT> of(String key, Function<CONTEXT, String> replacement) {
        return Placeholder.<CONTEXT>builder()
            .with(key, replacement)
            .build();
    }

    public static class Builder<CONTEXT> {

        private final Map<String, Function<CONTEXT, String>> placeholders = new HashMap<>();

        public Builder<CONTEXT> with(String from, Function<CONTEXT, String> replacement) {
            this.placeholders.put(from, replacement);
            return this;
        }

        public Placeholder<CONTEXT> build() {
            return new Placeholder<>(this.placeholders);
        }

    }

}
