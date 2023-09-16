package com.eternalcode.core.injector.annotations.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class is an event listener.
 * Classes annotated with this annotation will be registered as event listeners.
 *
 * @see Component
 * @see Service
 * @see Repository
 * @see Task
 * @see ConfigurationFile
 * @see BeanSetup
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
}
