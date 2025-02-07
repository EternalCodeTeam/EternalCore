package com.eternalcode.core.feature.tellraw;

import com.eternalcode.core.injector.annotations.component.Service;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
class TellRawService {

    private final Map<UUID, Set<TellRawNotice>> multipleNotices = new HashMap<>();

    void addNotice(UUID uuid, TellRawNotice notice) {
        Set<TellRawNotice> notices = this.multipleNotices.computeIfAbsent(uuid, k -> new HashSet<>());

        notices.add(notice);
        this.multipleNotices.put(uuid, notices);
    }

    Set<TellRawNotice> getNotices(UUID uuid) {
        return this.multipleNotices.get(uuid);
    }

    void removeNotices(UUID uuid) {
        this.multipleNotices.remove(uuid);
    }

    boolean hasNotices(UUID uuid) {
        return this.multipleNotices.containsKey(uuid);
    }
}
