package com.eternalcode.core.injector.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class BeanCandidateContainer {

    private final Set<BeanCandidate> candidates = ConcurrentHashMap.newKeySet();

    void addCandidate(BeanCandidate candidate) {
        this.candidates.add(candidate);
    }

    void removeCandidate(BeanCandidate candidate) {
        this.candidates.remove(candidate);
    }

    List<BeanCandidate> getCandidates(Class<?> type) {
        List<BeanCandidate> candidates = new ArrayList<>();

        for (BeanCandidate candidate : this.candidates) {
            if (candidate.isCandidate(type)) {
                candidates.add(candidate);
            }
        }

        return candidates;
    }

    Set<BeanCandidate> getCandidatesCopy() {
        return Collections.unmodifiableSet(this.candidates);
    }

}
