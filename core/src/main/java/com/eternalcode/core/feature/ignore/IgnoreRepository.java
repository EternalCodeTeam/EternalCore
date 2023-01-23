package com.eternalcode.core.feature.ignore;

import panda.std.Blank;
import panda.std.reactive.Completable;

import java.util.UUID;

public interface IgnoreRepository {

    Completable<Boolean> isIgnored(UUID by, UUID target);

    Completable<Blank> ignore(UUID by, UUID target);

    Completable<Blank> ignoreAll(UUID by);

    Completable<Blank> unIgnore(UUID by, UUID target);

    Completable<Blank> unIgnoreAll(UUID by);

}
