package com.eternalcode.core.command.argument.home;

import dev.rollczi.litecommands.injector.Injectable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Injectable
public @interface ArgHome {
}
