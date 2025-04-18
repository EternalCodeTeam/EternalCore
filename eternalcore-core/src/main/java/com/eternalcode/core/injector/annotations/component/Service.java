package com.eternalcode.core.injector.annotations.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class is a service.
 * Classes annotated with this annotation will be instantiated by the bean factory and will be available for injection.
 *
 * @see Component
 * @see Repository
 * @see Task
 * @see Controller
 * @see ConfigurationFile
 * @see Setup
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {

    /**
     * The name of the service.
     */
    String value() default "";

}
