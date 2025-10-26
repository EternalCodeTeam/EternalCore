package com.eternalcode.annotations.scan.placeholder;

public record PlaceholderResult(
    String name,
    String description,
    String returnType,
    String category,
    boolean requiresPlayer
) {
}
