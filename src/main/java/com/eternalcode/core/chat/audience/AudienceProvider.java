package com.eternalcode.core.chat.audience;

import com.eternalcode.core.user.User;

import java.util.Collection;
import java.util.UUID;

public interface AudienceProvider {

    Collection<Audience> all();

    Collection<Audience> allPlayers();

    Audience console();

    Audience player(UUID uuid);

    Audience user(User user);

}
