package com.eternalcode.core.injector.bean.processor;

import com.eternalcode.core.injector.DependencyProvider;
import com.eternalcode.core.injector.bean.BeanHolder;
import com.eternalcode.core.util.ReflectUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanProcessor {

    private final Map<Class<? extends Annotation>, BeanProcessorRegistry> processors = new HashMap<>();

    <A extends Annotation, BEAN> BeanProcessor onProcess(Class<A> annotation, Class<BEAN> bean, Processor<BEAN, A> processor) {
        BeanProcessorRegistry registry = this.processors.computeIfAbsent(annotation, c -> new BeanProcessorRegistry());
        registry.register(bean, processor);
        return this;
    }

    <BEAN> BeanProcessor onProcess(Class<BEAN> bean, Processor<BEAN, NoneAnnotation> processor) {
        return this.onProcess(NoneAnnotation.class, bean, processor);
    }

    public <BEAN> void process(DependencyProvider dependencyProvider, BeanHolder<BEAN> bean) {
        for (Annotation annotation : bean.getAnnotations()) {
            this.process(dependencyProvider, annotation, bean);
        }

        this.process(dependencyProvider, NoneAnnotation.INSTANCE, bean);
    }

    private <A extends Annotation, BEAN> void process(DependencyProvider dependencyProvider, A annotation, BeanHolder<BEAN> bean) {
        BeanProcessorRegistry registry = this.processors.get(annotation.annotationType());

        if (registry == null) {
            return;
        }

        for (Class<?> superClass : ReflectUtil.getAllSuperClasses(bean.getType())) {
            Set<Processor<BEAN, A>> beanProcessors = ReflectUtil.unsafeCast(registry.getProcessors(superClass));

            if (beanProcessors == null) {
                continue;
            }

            for (Processor<BEAN, A> processor : beanProcessors) {
                processor.process(dependencyProvider, bean.get(), annotation);
            }
        }
    }


}
