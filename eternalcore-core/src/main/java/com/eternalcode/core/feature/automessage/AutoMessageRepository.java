package com.eternalcode.core.feature.automessage;

import panda.std.reactive.Completable;

import java.util.Set;
import java.util.UUID;

public interface AutoMessageRepository {

    Completable<Set<UUID>> findReceivers(Set<UUID> onlineUniqueIds);

    // for nearby feafure like placeholders etc
    Completable<Boolean> isReceiving(UUID uniqueId);

    Completable<Boolean> switchReceiving(UUID uniqueId);

}
