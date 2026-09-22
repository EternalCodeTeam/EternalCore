package com.eternalcode.core.modules;

import com.eternalcode.core.injector.annotations.Inject;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

class ModuleDependencyGraph {

    private final Map<String, Set<String>> dependentsByRequiredModule = new LinkedHashMap<>();

    static ModuleDependencyGraph build(Collection<Class<?>> scannedTypes) {
        ModuleDependencyGraph graph = new ModuleDependencyGraph();

        for (Class<?> type : scannedTypes) {
            Constructor<?> injectConstructor = findInjectConstructor(type);

            if (injectConstructor == null) {
                continue;
            }

            for (Class<?> parameterType : injectConstructor.getParameterTypes()) {
                graph.registerDependency(type, parameterType);
            }
        }

        return graph;
    }

    Set<String> dependentsOf(String requiredModuleId) {
        return this.dependentsByRequiredModule.getOrDefault(requiredModuleId, Set.of());
    }

    private void registerDependency(Class<?> owner, Class<?> required) {
        String ownerModule = ModuleService.resolveModuleId(owner);
        String requiredModule = ModuleService.resolveModuleId(required);

        if (ownerModule == null || requiredModule == null || ownerModule.equals(requiredModule)) {
            return;
        }

        this.dependentsByRequiredModule
            .computeIfAbsent(requiredModule, key -> new LinkedHashSet<>())
            .add(ownerModule);
    }

    private static Constructor<?> findInjectConstructor(Class<?> type) {
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                return constructor;
            }
        }

        return null;
    }

}
