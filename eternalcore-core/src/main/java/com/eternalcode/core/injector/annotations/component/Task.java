package com.eternalcode.core.injector.annotations.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Task annotation is used to mark a class as a task.
 * The class must implement {@link Runnable}.
 *
 * @see Component
 * @see Service
 * @see Repository
 * @see Controller
 * @see ConfigurationFile
 * @see Setup
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Task {

    /**
     * The delay before the task is executed.
     *
     * @return the delay
     */
    long delay();

    /**
     * The period between each execution of the task.
     * If the value is 0, the task will only be executed once.
     *
     * @return the period
     */
    long period() default 0L;

    /**
     * The time unit of the delay and period.
     *
     * @return the time unit
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}
