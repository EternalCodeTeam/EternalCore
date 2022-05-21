package com.eternalcode.core.viewer;

import com.eternalcode.core.user.User;

import java.util.Collection;
import java.util.UUID;

public interface ViewerProvider {

    Collection<Viewer> all();

    Collection<Viewer> allPlayers();

    Viewer console();

    Viewer player(UUID uuid);

    Viewer user(User user);

}
