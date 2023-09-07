package com.eternalcode.core.injector.bean.processor;

import java.lang.annotation.Annotation;

class NoneAnnotation implements Annotation {

    public static final NoneAnnotation INSTANCE = new NoneAnnotation();

    @Override
    public Class<? extends Annotation> annotationType() {
        return NoneAnnotation.class;
    }

}
