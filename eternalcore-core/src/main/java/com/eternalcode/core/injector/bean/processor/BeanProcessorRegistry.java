package com.eternalcode.core.injector.bean.processor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class BeanProcessorRegistry {

    private final Map<Class<?>, Set<Processor<?, ?>>> registry = new HashMap<>();

    <BEAN, A extends Annotation> void register(Class<BEAN> bean, Processor<BEAN, A> processor) {
        this.registry.computeIfAbsent(bean, c -> new HashSet<>()).add(processor);
    }

    Set<Processor<?, ?>> getProcessors(Class<?> bean) {
        return this.registry.get(bean);
    }
}
