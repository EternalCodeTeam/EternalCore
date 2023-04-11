package com.eternalcode.annotations.scan.feature;

import com.eternalcode.annotations.scan.EternalScanRecord;
import com.eternalcode.annotations.scan.SingleAnnotationScanResolver;

;

public class FeatureScanResolver extends SingleAnnotationScanResolver<FeatureDocs, FeatureResult> {

    public FeatureScanResolver() {
        super(FeatureDocs.class);
    }

    @Override
    protected FeatureResult resolve(EternalScanRecord record, FeatureDocs annotation) {
        return new FeatureResult(
                annotation.name(),
                annotation.permission(),
                annotation.description()
        );
    }

}
