package com.eternalcode.core.injector;

import com.eternalcode.core.util.ReflectUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DependencyScanner {

    private final Set<String> packages = new LinkedHashSet<>();
    private final Set<Class<?>> classes = new LinkedHashSet<>();
    private final DependencyProcessors processors;

    public DependencyScanner(DependencyProcessors processors) {
        this.processors = processors;
    }

    public DependencyScanner packages(String... packages) {
        this.packages.addAll(Arrays.asList(packages));
        return this;
    }

    public DependencyScanner classes(Class<?>... classes) {
        this.classes.addAll(Arrays.asList(classes));
        return this;
    }

    List<LazyDependency<?, ?>> scan() {
        List<LazyDependency<?, ?>> dependencyRegistry = new ArrayList<>();

        this.classes.addAll(this.scanPackages());
        List<InjectIllegalDependencyException> illegalDependencies = new ArrayList<>();

        for (Class<?> classToRegister : this.classes) {
            if (!this.processors.canProcess(classToRegister)) {
                continue;
            }

            try {
                dependencyRegistry.addAll(this.getLazyDependencies(classToRegister));
            }
            catch (InjectIllegalDependencyException exception) {
                illegalDependencies.add(exception);
            }
        }

        if (!illegalDependencies.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Illegal dependencies: ");

            for (InjectIllegalDependencyException illegalDependency : illegalDependencies) {
                errorMessage
                    .append(System.lineSeparator())
                    .append(" - ")
                    .append(illegalDependency.getMessage());
            }

            throw new InjectException(errorMessage.toString());
        }

        return dependencyRegistry;
    }

    private <T> List<LazyDependency<?, ?>> getLazyDependencies(Class<T> classToRegister) {
        return LazyDependency.scanClassForDependencies(classToRegister);
    }

    private List<Class<?>> scanPackages() {
        return this.packages.stream()
            .map(packageName -> this.scanPackage(packageName))
            .flatMap(classesFromPackage -> classesFromPackage.stream())
            .toList();
    }

    private List<Class<?>> scanPackage(String packageName) {
        return ReflectUtil.scanClasses(packageName, this.getClass().getClassLoader());
    }

}
