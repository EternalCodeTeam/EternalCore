package com.eternalcode.core.injector.annotations.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BeanSetup annotation is used to mark a class as a holder for beans.
 *
 * @see Component
 * @see Service
 * @see Repository
 * @see Task
 * @see Controller
 * @see ConfigurationFile
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BeanSetup {

}
