package com.eternalcode.annotations.scan.placeholder;

import com.eternalcode.annotations.scan.EternalScanRecord;
import com.eternalcode.annotations.scan.SingleAnnotationScanResolver;

public class PlaceholderScanResolver extends SingleAnnotationScanResolver<PlaceholderDocs, PlaceholderResult> {

    public PlaceholderScanResolver() {
        super(PlaceholderDocs.class);
    }

    @Override
    public PlaceholderResult resolve(EternalScanRecord record, PlaceholderDocs annotation) {
        return new PlaceholderResult(
            annotation.name(),
            annotation.description(),
            annotation.example(),
            annotation.returnType(),
            annotation.category(),
            annotation.requiresPlayer()
        );
    }
}
