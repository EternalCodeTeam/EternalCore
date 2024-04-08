package com.eternalcode.core.feature.essentials.tellraw;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class TellRawService {

    private final Map<UUID, Set<TellRawNotice>> multipleNotices = new HashMap<>();

    @Inject
    TellRawService() {}

    void addNotice(UUID uuid, TellRawNotice notice) {
        Set<TellRawNotice> notices = this.multipleNotices.get(uuid);

        if (notices == null) {
            notices = new HashSet<>();
        }

        notices.add(notice);
        this.multipleNotices.put(uuid, notices);
    }

    Set<TellRawNotice> getNotices(UUID uuid) {
        return this.multipleNotices.get(uuid);
    }

    void removeNotices(UUID uuid) {
        this.multipleNotices.remove(uuid);
    }
}
