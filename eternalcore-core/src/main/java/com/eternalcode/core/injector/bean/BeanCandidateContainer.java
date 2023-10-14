package com.eternalcode.core.injector.bean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

class BeanCandidateContainer {

    private final Object lock = new Object();

    private final Set<BeanCandidate> candidates = new HashSet<>();

    void addCandidate(BeanCandidate candidate) {
        synchronized (this.lock) {
            this.candidates.add(candidate);
        }
    }

    void removeCandidate(BeanCandidate candidate) {
        synchronized (this.lock) {
            this.candidates.remove(candidate);
        }
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
            Iterator<BeanCandidate> iterator = this.candidates.iterator();

            if (!iterator.hasNext()) {
                return null;
            }

            BeanCandidate candidate = iterator.next();
            iterator.remove();
            return candidate;
        }
    }

}
