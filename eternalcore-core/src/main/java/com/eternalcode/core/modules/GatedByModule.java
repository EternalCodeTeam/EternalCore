package com.eternalcode.core.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For a bean that is not a listener or command but still has a side effect on construction (e.g.
 * scheduling a recurring task, registering a raw Bukkit command) - marks it as gated by its
 * module's modules.yml entry, same as {@code @Controller}/{@code @Command} already are. Only use
 * this on beans nothing outside their own module depends on, otherwise disabling the module would
 * make that other dependency fail to resolve.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GatedByModule {

}
