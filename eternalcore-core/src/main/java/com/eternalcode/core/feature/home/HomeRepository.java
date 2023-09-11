package com.eternalcode.core.feature.home;

import com.eternalcode.core.user.User;
import panda.std.Blank;
import panda.std.Option;
import panda.std.reactive.Completable;

import java.util.Set;
import java.util.UUID;

interface HomeRepository {

    Completable<Option<Home>> getHome(UUID uuid);

    Completable<Option<Home>> getHome(User user, String name);

    Completable<Blank> saveHome(Home home);

    Completable<Integer> deleteHome(UUID uuid);

    Completable<Integer> deleteHome(User user, String name);

    Completable<Set<Home>> getHomes();

    Completable<Set<Home>> getHomes(User user);

}
