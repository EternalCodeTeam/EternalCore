package com.eternalcode.annotations.scan;

import com.eternalcode.annotations.scan.reflect.AnnotationUtil;
import java.lang.annotation.Annotation;
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
        List<A> annotations = AnnotationUtil.scanForAnnotations(record.clazz(), this.annotationClass);

        for (A annotation : annotations) {
            results.add(this.resolve(record, annotation));
        }

        return results;
    }

    protected abstract RESULT resolve(EternalScanRecord record, A annotation);

}
