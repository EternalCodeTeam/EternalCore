package com.eternalcode.core.compatibility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Compatibility {

    Version from() default @Version(minor = Integer.MIN_VALUE, patch = Integer.MIN_VALUE);

    Version to() default @Version(minor = Integer.MAX_VALUE, patch = Integer.MAX_VALUE);

}
