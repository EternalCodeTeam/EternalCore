package com.eternalcode.core.dependencyyy.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanCandidateContainer {

    private final Set<BeanCandidate> candidates = new HashSet<>();

    public void addCandidate(BeanCandidate candidate) {
        this.candidates.add(candidate);
    }

    public List<BeanCandidate> getCandidates(Class<?> type) {
        List<BeanCandidate> candidates = new ArrayList<>();

        for (BeanCandidate candidate : this.candidates) {
            if (candidate.isCandidate(type)) {
                candidates.add(candidate);
            }
        }

        return candidates;
    }

}
