package com.eternalcode.annotations.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class SingleAnnotationScanResolver<A extends Annotation, RESULT> implements EternalScanResolver<RESULT> {

    private final Class<A> annotationClass;

    public SingleAnnotationScanResolver(Class<A> annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public final List<RESULT> resolve(EternalScanRecord record) {
        List<RESULT> results = new ArrayList<>();
        List<A> annotations = this.scanForAnnotations(record.clazz(), this.annotationClass);

        for (A annotation : annotations) {
            results.add(this.resolve(record, annotation));
        }

        return results;
    }

    protected abstract RESULT resolve(EternalScanRecord record, A annotation);

    private List<A> scanForAnnotations(Class<?> classToScan, Class<A> annotationClass) {
        List<A> annotations = new ArrayList<>();

        A annotation = classToScan.getAnnotation(annotationClass);

        if (annotation != null) {
            annotations.add(annotation);
        }

        for (Method method : classToScan.getDeclaredMethods()) {
            A methodAnnotation = method.getAnnotation(annotationClass);

            if (methodAnnotation != null) {
                annotations.add(methodAnnotation);
            }
        }

        for (Field field : classToScan.getDeclaredFields()) {
            A fieldAnnotation = field.getAnnotation(annotationClass);

            if (fieldAnnotation != null) {
                annotations.add(fieldAnnotation);
            }
        }

        return annotations;
    }

}
