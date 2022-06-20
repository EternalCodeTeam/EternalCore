package com.eternalcode.core.database;

import com.eternalcode.core.chat.feature.ingore.IgnoreRepository;
import com.eternalcode.core.home.Home;
import com.eternalcode.core.home.HomeRepository;
import com.eternalcode.core.user.User;
import panda.std.Blank;
import panda.std.Option;
import panda.std.Result;
import panda.std.function.ThrowingSupplier;
import panda.std.reactive.Completable;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Logger;

public final class NoneRepository implements HomeRepository, IgnoreRepository {

    @Override
    public Completable<Option<Home>> getHome(UUID uuid) {
        return Completable.completed(Option.none());
    }

    @Override
    public Completable<Option<Home>> getHome(User user, String name) {
        return Completable.completed(Option.none());
    }

    @Override
    public Completable<Blank> saveHome(Home home) {
        return Completable.completed(Blank.BLANK);
    }

    @Override
    public Completable<Blank> deleteHome(UUID uuid) {
        return Completable.completed(Blank.BLANK);
    }

    @Override
    public Completable<Blank> deleteHome(User user, String name) {
        return Completable.completed(Blank.BLANK);
    }

    @Override
    public Completable<Set<Home>> getHomes() {
        return Completable.completed(Collections.emptySet());
    }

    @Override
    public Completable<Set<Home>> getHomes(User user) {
        return Completable.completed(Collections.emptySet());
    }

    @Override
    public Completable<Boolean> isIgnored(UUID by, UUID target) {
        return Completable.completed(false);
    }

    @Override
    public void ignore(UUID by, UUID target) {}

    @Override
    public void unIgnore(UUID by, UUID target) {}

}
