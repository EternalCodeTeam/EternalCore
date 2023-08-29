package com.eternalcode.core.feature.automessage;

import panda.std.reactive.Completable;

import java.util.List;
import java.util.UUID;

public interface AutoMessageRepository {

    Completable<List<UUID>> findReceivers(List<UUID> onlineUniqueIds);

    // for nearby feafure like placeholders etc
    Completable<Boolean> isReceiving(UUID uniqueId);

    Completable<Boolean> switchReceiving(UUID uniqueId);

}
