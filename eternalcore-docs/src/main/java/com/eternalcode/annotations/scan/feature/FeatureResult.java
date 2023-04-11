package com.eternalcode.annotations.scan.feature;

public record FeatureResult(
        String name,
        String[] permissions,
        String[] descriptions
) { }
