package com.eternalcode.core.injector.annotations.lite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to register arguments for LiteCommands
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LiteArgument {

    Class<?> type();

    String name() default "";

}
