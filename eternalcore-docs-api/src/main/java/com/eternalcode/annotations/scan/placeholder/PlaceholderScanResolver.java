package com.eternalcode.annotations.scan.placeholder;

import com.eternalcode.annotations.scan.EternalScanRecord;
import com.eternalcode.annotations.scan.GroupAnnotationScanResolver;

public class PlaceholderScanResolver extends GroupAnnotationScanResolver<PlaceholdersDocs.Entry, PlaceholdersDocs, PlaceholderResult> {

    public PlaceholderScanResolver() {
        super(PlaceholdersDocs.Entry.class, PlaceholdersDocs.class);
    }

    @Override
    protected PlaceholderResult resolve(EternalScanRecord record, PlaceholdersDocs group, PlaceholdersDocs.Entry placeholder) {
        String prefixedName = "%eternalcore_" + placeholder.name() + "%";
        return new PlaceholderResult(
            prefixedName,
            placeholder.description(),
            placeholder.returnType().getName(),
            group.category(),
            placeholder.requiresPlayer()
        );
    }

}
