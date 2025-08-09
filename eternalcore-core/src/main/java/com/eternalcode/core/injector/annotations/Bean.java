package com.eternalcode.core.injector.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Bean {

    String value() default "";

    /**
     * Specifies the interface to be used for proxying the bean.
     * <p>
     * When a proxy interface is provided, the dependency injector will create a dynamic proxy
     * that implements the specified interface. This is particularly useful for lazily
     * initialized fields and reloading, where the actual instance of the field is retrieved only when a
     * method is invoked on the interface.
     * <p>
     * The field's class or the method's return type must be assignable to the specified interface.
     * Otherwise, a {@link com.eternalcode.core.injector.bean.BeanException} will be thrown.
     *
     * @return the interface to proxy, or {@code Class.class} if no proxy is needed.
     */
    Class<?> proxied() default Class.class;

}
