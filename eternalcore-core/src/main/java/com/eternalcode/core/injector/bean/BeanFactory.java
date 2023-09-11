package com.eternalcode.core.injector.bean;

import com.eternalcode.core.injector.DependencyProvider;
import com.eternalcode.core.injector.bean.processor.BeanProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BeanFactory implements DependencyProvider {

    private final static ThreadLocal<List<Class<?>>> dependencyStacktrace = ThreadLocal.withInitial(() -> new ArrayList<>());

    private final BeanContainer container = new BeanContainer();
    private final BeanCandidateContainer candidateContainer = new BeanCandidateContainer();
    private final BeanProcessor beanProcessor;

    public BeanFactory(BeanProcessor beanProcessor) {
        this.beanProcessor = beanProcessor;
    }

    @Override
    public <T> T getDependency(Class<T> clazz) {
        return this.getSingletonBean(clazz).get();
    }

    private <T> BeanHolder<T> getSingletonBean(Class<T> type) {
        List<Class<?>> stacktrace = dependencyStacktrace.get();

        if (stacktrace.contains(type)) {
            stacktrace.add(type);
            String cycledDependencies = stacktrace.stream()
                .map(dependencyType -> dependencyType.getName())
                .collect(Collectors.joining(System.lineSeparator() + " -> "));
            stacktrace.remove(stacktrace.size() - 1);

            throw new BeanException("Cycled dependency detected! [" + cycledDependencies + "]", type);
        }

        stacktrace.add(type);

        try {
            List<BeanHolder<T>> beans = this.container.getBeans(type);

            if (beans.isEmpty()) {
                return this.createBeanFromCandidate(type)
                    .orElseThrow(() -> new BeanException("No bean found for type " + type.getName(), type));
            }

            if (beans.size() > 1) {
                String beansAsString = String.join(", ", beans.stream()
                    .map(bean -> bean.get().toString())
                    .toArray(String[]::new));

                throw new BeanException("Multiple beans found for type " + type.getName() + ": " + beansAsString, type);
            }

            return beans.get(0);
        }
        finally {
            stacktrace.remove(stacktrace.size() - 1);
        }
    }

    public <T> List<BeanHolder<T>> getBeans(Class<T> type) {
        return this.container.getBeans(type);
    }

    private <T> Optional<BeanHolder<T>> createBeanFromCandidate(Class<T> type) {
        BeanCandidate beanCandidate = this.candidateContainer.nextCandidate(type);

        if (beanCandidate == null) {
            return Optional.empty();
        }

        BeanHolder<T> bean = this.initializeCandidate(beanCandidate, type);

        return Optional.of(bean);
    }

    public BeanFactory addCandidate(BeanCandidate beanCandidate) {
        this.candidateContainer.addCandidate(beanCandidate);
        return this;
    }

    public BeanFactory withCandidateSelf() {
        return this.addCandidate(BeanFactory.class, () -> this);
    }

    public <T> BeanFactory addCandidate(Class<T> type, Supplier<T> instance) {
        return this.addCandidate(BeanCandidate.of(type, instance));
    }

    public BeanFactory initializeCandidates(Class<?> type) {
        BeanCandidate candidate;

        while ((candidate = this.candidateContainer.nextCandidate(type)) != null) {
            this.initializeCandidate(candidate, type);
        }

        return this;
    }

    public BeanFactory initializeCandidates() {
        BeanCandidate candidate;

        while ((candidate = this.candidateContainer.nextCandidate()) != null) {
            this.initializeCandidate(candidate, Object.class);
        }

        return this;
    }

    private <T> BeanHolder<T> initializeCandidate(BeanCandidate candidate, Class<T> asType) {
        BeanHolder<T> bean = candidate.createBean(asType);

        this.container.addBean(bean);
        this.candidateContainer.removeCandidate(candidate);
        this.beanProcessor.process(this, bean);

        return bean;
    }

}
