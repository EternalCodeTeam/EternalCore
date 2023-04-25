package com.eternalcode.core.util;

public final class LabeledOptionUtil {

    private LabeledOptionUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static LabeledOption<String> ofString(String option) {
        return new LabeledOption<>(option);
    }

    public static class LabeledOption<T> {

        private final T option;

        public LabeledOption(T option) {
            this.option = option;
        }

        public T getOption() {
            return this.option;
        }
    }

}
