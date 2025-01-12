package com.eternalcode.core.injector.scan;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface ComponentNameProvider<COMPONENT extends Annotation> {

    String getName(COMPONENT component);

}
