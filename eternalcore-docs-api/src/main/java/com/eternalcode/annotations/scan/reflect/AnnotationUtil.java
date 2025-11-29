package com.eternalcode.annotations.scan.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtil {

    public static <A extends Annotation> List<A> scanForAnnotations(Class<?> classToScan, Class<A> annotationClass) {
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
