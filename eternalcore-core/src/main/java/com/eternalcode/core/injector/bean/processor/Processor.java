package com.eternalcode.core.injector.bean.processor;

import com.eternalcode.core.injector.DependencyProvider;

import java.lang.annotation.Annotation;

interface Processor<T, A extends Annotation> {

    void process(DependencyProvider dependencyProvider, T bean, A annotation);

}
