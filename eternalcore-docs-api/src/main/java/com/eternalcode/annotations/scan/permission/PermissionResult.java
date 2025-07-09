package com.eternalcode.annotations.scan.permission;

public record PermissionResult(
    String name,
    String[] permissions,
    String[] descriptions
) {
} 