package com.eternalcode.core.injector.bean;

import com.eternalcode.core.injector.DependencyProvider;
import com.eternalcode.core.injector.bean.processor.BeanProcessor;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class BeanFactory implements DependencyProvider {

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

    public <T> BeanHolder<T> getSingletonBean(Class<T> type) {
        List<BeanHolder<T>> beans = this.container.getBeans(type);

        if (beans.isEmpty()) {
            return this.createBeanFromCandidate(type)
                .orElseThrow(() -> new BeanException("No bean found for type " + type.getName(), type));
        }

        if (beans.size() > 1) {
            String beansAsString = String.join(", ", beans.stream()
                .map(bean -> bean.getType().getName())
                .toArray(String[]::new));

            throw new BeanException("Multiple beans found for type " + type.getName() + ": " + beansAsString, type);
        }

        return beans.get(0);
    }

    public <T> List<BeanHolder<T>> getBeans(Class<T> type) {
        return this.container.getBeans(type);
    }

    private <T> Optional<BeanHolder<T>> createBeanFromCandidate(Class<T> type) {
        List<BeanCandidate> candidates = this.candidateContainer.getCandidates(type);

        if (candidates.isEmpty()) {
            return Optional.empty();
        }

        if (candidates.size() > 1) {
            String candidatesAsString = String.join(", ", candidates.stream()
                .map(candidate -> candidate.toString())
                .toArray(String[]::new));

            throw new BeanException("Multiple candidates found for type " + type.getName() + ": " + candidatesAsString, type);
        }

        BeanCandidate candidate = candidates.get(0);
        BeanHolder<T> bean = this.initializeCandidate(candidate, type);

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

    public BeanFactory initializeCandidates(Class<?> type ) {
        for (BeanCandidate candidate : this.candidateContainer.getCandidates(type)) {
            this.initializeCandidate(candidate, type);
        }

        return this;
    }

    public BeanFactory initializeCandidates() {
        for (BeanCandidate candidate : this.candidateContainer.getCandidatesCopy()) {
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
