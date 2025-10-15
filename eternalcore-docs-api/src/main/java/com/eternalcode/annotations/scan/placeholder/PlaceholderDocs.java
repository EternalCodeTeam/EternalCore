package com.eternalcode.annotations.scan.placeholder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface PlaceholderDocs {

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
     * Example output of the placeholder
     */
    String example() default "";

    /**
     * Return type of the placeholder
     */
    String returnType() default "String";

    /**
     * Category for grouping placeholders
     */
    String category() default "General";

    /**
     * Whether the placeholder requires a player context
     */
    boolean requiresPlayer() default true;
}
