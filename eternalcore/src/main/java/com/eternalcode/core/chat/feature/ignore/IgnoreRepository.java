package com.eternalcode.core.chat.feature.ignore;

import panda.std.reactive.Completable;

import java.util.UUID;

public interface IgnoreRepository {

    Completable<Boolean> isIgnored(UUID by, UUID target);

    void ignore(UUID by, UUID target);

    void unIgnore(UUID by, UUID target);

}
