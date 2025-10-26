package com.eternalcode.annotations.scan;

import com.eternalcode.annotations.scan.reflect.AnnotationUtil;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Stream;

public abstract class GroupAnnotationScanResolver<A extends Annotation, GROUP extends Annotation, RESULT> implements EternalScanResolver<RESULT> {

    private final Class<GROUP> annotationGroupClass;

    public GroupAnnotationScanResolver(Class<GROUP> annotationGroupClass) {
        this.annotationGroupClass = annotationGroupClass;
    }

    @Override
    public final List<RESULT> resolve(EternalScanRecord record) {
        return AnnotationUtil.scanForAnnotations(record.clazz(), this.annotationGroupClass).stream()
            .flatMap(group -> this.resolveGroup(record, group).stream())
            .toList();
    }

    private List<RESULT> resolveGroup(EternalScanRecord record, GROUP group) {
        return Stream.of(getAnnotationsForGroup(group))
            .map(annotation -> this.resolve(record, group, annotation))
            .toList();
    }

    protected abstract A[] getAnnotationsForGroup(GROUP group);

    protected abstract RESULT resolve(EternalScanRecord record, GROUP group, A annotation);


}
