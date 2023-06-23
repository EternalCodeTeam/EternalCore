package com.eternalcode.core.database;

import com.eternalcode.core.feature.automessage.AutoMessageRepository;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeRepository;
import com.eternalcode.core.feature.ignore.IgnoreRepository;
import com.eternalcode.core.user.User;
import panda.std.Blank;
import panda.std.Option;
import panda.std.reactive.Completable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class NoneRepository implements HomeRepository, IgnoreRepository, AutoMessageRepository {

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
    public Completable<Integer> deleteHome(UUID uuid) {
        return Completable.completed(0);
    }

    @Override
    public Completable<Integer> deleteHome(User user, String name) {
        return Completable.completed(0);
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
    public Completable<Blank> ignore(UUID by, UUID target) {
        return Completable.completed(Blank.BLANK);
    }

    @Override
    public Completable<Blank> ignoreAll(UUID by) {
        return Completable.completed(Blank.BLANK);
    }

    @Override
    public Completable<Blank> unIgnore(UUID by, UUID target) {
        return Completable.completed(Blank.BLANK);
    }

    @Override
    public Completable<Blank> unIgnoreAll(UUID by) {
        return Completable.completed(Blank.BLANK);
    }

    @Override
    public Completable<List<UUID>> findRecivers(List<UUID> onlineUniqueIds) {
        return Completable.completed(Collections.emptyList());
    }

    @Override
    public Completable<Boolean> isReciving(UUID uniqueId) {
        return Completable.completed(false);
    }

    @Override
    public Completable<Boolean> switchReciving(UUID uniqueId) {
        return Completable.completed(false);
    }
}
