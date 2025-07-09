package com.eternalcode.annotations.scan.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface PermissionDocs {

    String name() default "";

    String[] permission() default {};

    String[] description() default {};

} 
