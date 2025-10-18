package com.eternalcode.core.injector.bean;

import dev.rollczi.litecommands.priority.MutablePrioritizedList;
import dev.rollczi.litecommands.priority.PriorityLevel;
import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

class BeanCandidateContainer {

    private final Object lock = new Object();

    private final MutablePrioritizedList<BeanCandidate> candidates = new MutablePrioritizedList<>();
    private Function<BeanCandidate, PriorityLevel> priorityProvider = beanCandidate -> PriorityLevel.NORMAL;

    void addCandidate(BeanCandidate candidate) {
        PriorityLevel priorityLevel = this.priorityProvider.apply(candidate);
        if (!priorityLevel.equals(candidate.getPriority())) {
            candidate = BeanCandidate.prioritized(candidate, priorityLevel);
        }

        synchronized (this.lock) {
            this.candidates.add(candidate);
        }
    }

    void removeCandidate(BeanCandidate candidate) {
        synchronized (this.lock) {
            this.candidates.remove(candidate);
        }
    }

    void setPriorityProvider(Function<BeanCandidate, PriorityLevel> priorityProvider) {
        this.priorityProvider = priorityProvider;
    }

    @Nullable
    BeanCandidate nextCandidate(Class<?> type) {
        synchronized (this.lock) {
            for (BeanCandidate candidate : this.candidates) {
                if (!candidate.isCandidate(type)) {
                    continue;
                }

                this.candidates.remove(candidate);
                return candidate;
            }
        }

        return null;
    }

    @Nullable
    BeanCandidate nextCandidate() {
        synchronized (this.lock) {
            if (this.candidates.isEmpty()) {
                return null;
            }

            BeanCandidate candidate = this.candidates.first();
            this.candidates.remove(candidate);
            return candidate;
        }
    }
}
