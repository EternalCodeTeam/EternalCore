package com.eternalcode.annotations.scan;

import com.eternalcode.annotations.scan.reflect.AnnotationUtil;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class GroupAnnotationScanResolver<A extends Annotation, G extends Annotation, RESULT> implements EternalScanResolver<RESULT> {

    private final Class<A> annotationClass;
    private final Class<G> annotationGroupClass;

    public GroupAnnotationScanResolver(Class<A> annotationClass, Class<G> annotationGroupClass) {
        this.annotationClass = annotationClass;
        this.annotationGroupClass = annotationGroupClass;
    }

    @Override
    public final List<RESULT> resolve(EternalScanRecord record) {
        return AnnotationUtil.scanForAnnotations(record.clazz(), this.annotationGroupClass).stream()
            .flatMap(group -> this.resolveGroup(record, group).stream())
            .toList();
    }

    private List<RESULT> resolveGroup(EternalScanRecord record, G group) {
        return AnnotationUtil.scanForAnnotations(record.clazz(), this.annotationClass).stream()
            .map(annotation -> this.resolve(record, group, annotation))
            .toList();
    }

    protected abstract RESULT resolve(EternalScanRecord record, G group, A annotation);


}
