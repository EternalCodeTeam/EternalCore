package com.eternalcode.annotations.scan.placeholder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface PlaceholdersDocs {

    /**
     * Category for grouping placeholders
     */
    String category() default "General";

    Entry[] placeholders();

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @interface Entry {

        /**
         * The name of the placeholder (without % symbols)
         * Example: "afk" for %afk%
         */
        String name();

        /**
         * Description of what the placeholder does
         */
        String description();

        /**
         * Return type of the placeholder
         */
        Type returnType();

        enum Type {
            STRING("string"),
            BOOLEAN("boolean"),
            INT("int");

            private final String name;

            Type(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        /**
         * Whether the placeholder requires a player context
         */
        boolean requiresPlayer();
    }
}
