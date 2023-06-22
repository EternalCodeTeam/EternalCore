package com.eternalcode.core.feature.automessage;

import panda.std.reactive.Completable;

import java.util.List;
import java.util.UUID;

public interface AutoMessageRepository {

    Completable<List<UUID>> findRecivers(List<UUID> onlineUniqueIds);

    Completable<Boolean> isReciving(UUID uniqueId);

    Completable<Boolean> switchReciving(UUID uniqueId);

}
