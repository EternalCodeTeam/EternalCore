package com.eternalcode.annotations.scan;

import java.lang.reflect.Method;
import java.util.List;

public record EternalScanRecord(Class<?> clazz, List<Method> methods) {
}
