package com.eternalcode.core.injector.scan;

import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.bean.BeanCandidate;
import com.eternalcode.core.injector.bean.BeanHolder;
import com.eternalcode.core.injector.bean.LazyFieldBeanCandidate;
import com.eternalcode.core.util.ReflectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.jspecify.annotations.Nullable;

public class DependencyScanner {

    private final DependencyInjector dependencyInjector;
    private final Map<Class<? extends Annotation>, ComponentNameProvider<?>> annotations = new HashMap<>();
    private final List<Predicate<Class<?>>> includedTypes = new ArrayList<>();

    public DependencyScanner(DependencyInjector dependencyInjector) {
        this.dependencyInjector = dependencyInjector;
    }

    @SafeVarargs
    public final DependencyScanner includeAnnotations(Class<? extends Annotation>... annotationTypes) {
        for (Class<? extends Annotation> annotationType : annotationTypes) {
            this.includeAnnotation(annotationType);
        }

        return this;
    }

    public DependencyScanner includeType(Predicate<Class<?>> predicate) {
        this.includedTypes.add(predicate);
        return this;
    }

    private DependencyScanner includeAnnotation(Class<? extends Annotation> annotationType) {
        this.annotations.put(annotationType, annotation -> BeanHolder.DEFAULT_NAME);
        return this;
    }

    public <A extends Annotation> DependencyScanner includeAnnotation(Class<A> annotationType, ComponentNameProvider<A> componentNameProvider) {
        this.annotations.put(annotationType, componentNameProvider);
        return this;
    }

    public List<BeanCandidate> scan(Package... packages) {
        String[] packageNames = Arrays.stream(packages)
            .map(onePackage -> onePackage.getName())
            .toArray((int length) -> new String[length]);

        return this.scan(packageNames);
    }

    public List<BeanCandidate> scan(String... packageNames) {
        List<Class<?>> classes = this.scanPackages(List.of(packageNames));

        List<BeanCandidate> beanCandidates = new ArrayList<>();

        for (Class<?> clazz : classes) {
            boolean isIncluded = this.includedTypes.stream().allMatch(filter -> filter.test(clazz));
            if (!isIncluded) {
                continue;
            }

            beanCandidates.addAll(this.createBeanCandidates(clazz));
        }

        return beanCandidates;
    }

    private List<BeanCandidate> createBeanCandidates(Class<?> clazz) {
        List<BeanCandidate> beanCandidates = new ArrayList<>();
        BeanCandidate beanCandidate = this.createBeanCandidate(clazz);

        if (beanCandidate != null) {
            beanCandidates.add(beanCandidate);

            List<BeanCandidate> otherCandidates = this.getFieldAndMethodCandidates(clazz);
            beanCandidates.addAll(otherCandidates);
        }

        return beanCandidates;
    }

    private BeanCandidate createBeanCandidate(Class<?> clazz) {
        for (Annotation annotation : clazz.getAnnotations()) {
            BeanCandidate beanCandidate = this.createBeanCandidate(clazz, annotation);

            if (beanCandidate != null) {
                return beanCandidate;
            }
        }

        return null;
    }

    private <A extends Annotation> BeanCandidate createBeanCandidate(Class<?> clazz, A annotation) {
        ComponentNameProvider<A> componentNameProvider = this.getComponentNameProvider(annotation);

        if (componentNameProvider == null) {
            return null;
        }

        return new ComponentBeanCandidateImpl<>(this.dependencyInjector, clazz, annotation, componentNameProvider);
    }

    private List<BeanCandidate> getFieldAndMethodCandidates(Class<?> componentClass) {
        List<BeanCandidate> beanCandidates = new ArrayList<>();

        for (Method method : componentClass.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Bean.class)) {
                continue;
            }

            Bean bean = method.getAnnotation(Bean.class);
            BeanCandidate beanCandidate = new MethodBeanCandidate(this.dependencyInjector, componentClass, method, bean);

            beanCandidates.add(beanCandidate);
        }

        for (Field field : ReflectUtil.getAllSuperFields(componentClass)) {
            if (!field.isAnnotationPresent(Bean.class)) {
                continue;
            }

            Bean bean = field.getAnnotation(Bean.class);
            BeanCandidate beanCandidate = new LazyFieldBeanCandidate(this.dependencyInjector, componentClass, field, bean);

            beanCandidates.add(beanCandidate);
        }

        return beanCandidates;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <A extends Annotation> ComponentNameProvider<A> getComponentNameProvider(A annotation) {
        return (ComponentNameProvider<A>) this.annotations.get(annotation.annotationType());
    }

    private List<Class<?>> scanPackages(List<String> packages) {
        return packages.stream()
            .map(packageName -> this.scanPackage(packageName))
            .flatMap(classesFromPackage -> classesFromPackage.stream())
            .toList();
    }

    private List<Class<?>> scanPackage(String packageName) {
        return ReflectUtil.scanClasses(packageName, this.getClass().getClassLoader());
    }

}
