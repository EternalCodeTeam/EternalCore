package com.eternalcode.core.injector.bean;

import com.eternalcode.core.publish.SubscriberUtil;
import dev.rollczi.litecommands.priority.PriorityLevel;
import java.util.function.Function;

public class BeanCandidatePriorityProvider implements Function<BeanCandidate, PriorityLevel> {
    @Override
    public PriorityLevel apply(BeanCandidate candidate) {
        if (SubscriberUtil.isSubscriber(candidate.getType())) {
            return PriorityLevel.HIGH;
        }

        return PriorityLevel.NORMAL;
    }
}
