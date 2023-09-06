package com.eternalcode.core.injector;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.library.AdventureSetup;
import com.eternalcode.core.util.MapUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DependencyProcessors {

    private final Map<Class<?>, Map<Class<? extends Annotation>, DependencyProcessor<?>>> processors = new LinkedHashMap<>();

    public <T> DependencyProcessors on(Class<T> type, Class<? extends Annotation> annotation, DependencyProcessor<T> processor) {
        Map<Class<? extends Annotation>, DependencyProcessor<?>> annotationProcessors = this.processors.computeIfAbsent(type, k -> new HashMap<>());
        annotationProcessors.put(annotation, processor);
        return this;
    }

    public <T> DependencyProcessors on(Class<T> type, Class<? extends Annotation> annotation, Consumer<T> processor) {
        return this.on(type, annotation, (dependencyProvider, instance) -> processor.accept(instance));
    }

    public <T> DependencyProcessors on(Class<T> type, Class<? extends Annotation> annotation) {
        return this.on(type, annotation, (dependencyProvider, instance) -> {});
    }

    public DependencyProcessors on(Class<? extends Annotation> annotation, DependencyProcessor<Object> processor) {
        return this.on(Object.class, annotation, processor);
    }

    public DependencyProcessors on(Class<? extends Annotation> annotation, Consumer<Object> processor) {
        return this.on(Object.class, annotation, (dependencyProvider, instance) -> processor.accept(instance));
    }

    public DependencyProcessors on(Class<? extends Annotation> annotation) {
        return this.on(Object.class, annotation);
    }

    @SuppressWarnings("unchecked")
    <T> void process(DependencyProvider dependencyProvider, Dependency<T> dependency) {
        T t = dependency.get(dependencyProvider);
        Class<T> type = (Class<T>) t.getClass();
        List<DependencyProcessor<T>> processors = this.getProcessors(type, dependency.getAnnotations());

        for (DependencyProcessor<T> processor : processors) {
            processor.process(dependencyProvider, t);
        }
    }

    boolean canProcess(Class<?> classToProcess) {
        if (classToProcess == AdventureSetup.class) {
            return !this.getProcessors(classToProcess, Arrays.stream(classToProcess.getAnnotations()).toList()).isEmpty();
        }

        return !this.getProcessors(classToProcess, Arrays.stream(classToProcess.getAnnotations()).toList()).isEmpty();
    }

    @SuppressWarnings("unchecked")
    private <T> List<DependencyProcessor<T>> getProcessors(Class<T> type, List<Annotation> annotations) {
        // TODO get multiple type
        Map<Class<? extends Annotation>, DependencyProcessor<?>> annotationProcessors = MapUtil.findBySuperTypeOf(type, this.processors)
            .orElse(Collections.emptyMap());

        List<DependencyProcessor<T>> processors = new ArrayList<>();

        for (Annotation annotation : annotations) {
            DependencyProcessor<T> processor = (DependencyProcessor<T>) annotationProcessors.get(annotation.annotationType());

            if (processor != null) {
                processors.add(processor);
            }
        }

        if (processors.isEmpty()) {
            DependencyProcessor<T> processor = (DependencyProcessor<T>) annotationProcessors.get(Annotation.class);

            if (processor != null) {
                processors.add(processor);
            }
        }

        return processors;
    }

}
