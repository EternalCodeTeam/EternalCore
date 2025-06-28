package com.eternalcode.annotations.scan.permission;

import com.eternalcode.annotations.scan.EternalScanRecord;
import com.eternalcode.annotations.scan.SingleAnnotationScanResolver;

public class PermissionScanResolver extends SingleAnnotationScanResolver<PermissionDocs, PermissionResult> {

    public PermissionScanResolver() {
        super(PermissionDocs.class);
    }

    @Override
    public PermissionResult resolve(EternalScanRecord record, PermissionDocs annotation) {
        return new PermissionResult(
            annotation.name(),
            annotation.permission(),
            annotation.description()
        );
    }

} 