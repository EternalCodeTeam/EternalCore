package com.eternalcode.core.feature.alert;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.multification.notice.NoticeBroadcast;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
class AlertManager {

    private final static Set<NoticeTextType> DELAYED_TYPES = Set.of(NoticeTextType.TITLE, NoticeTextType.SUBTITLE, NoticeTextType.ACTIONBAR);

    private final Map<AlertKey, List<NoticeBroadcast<Viewer, Translation, ?>>> broadcasts = new ConcurrentHashMap<>();
    private final Scheduler scheduler;

    @Inject
    AlertManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    void addBroadcast(UUID uuid, NoticeTextType type, NoticeBroadcast<Viewer, Translation, ?> broadcast) {
        if (uuid == null || type == null || broadcast == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        this.broadcasts.computeIfAbsent(new AlertKey(uuid, type), k -> new ArrayList<>()).add(broadcast);
    }

    boolean removeBroadcastWithType(UUID uuid, NoticeTextType type) {
        return this.broadcasts.remove(new AlertKey(uuid, type)) != null;
    }

    boolean removeLatestBroadcastWithType(UUID uuid, NoticeTextType type) {
        AlertKey key = new AlertKey(uuid, type);
        List<NoticeBroadcast<Viewer, Translation, ?>> broadcasts = this.broadcasts.get(key);

        if (broadcasts != null && !broadcasts.isEmpty()) {
            broadcasts.remove(broadcasts.size() - 1);
            if (broadcasts.isEmpty()) {
                this.removeBroadcastWithType(uuid, type);
            }
            return true;
        }

        return false;
    }

    void clearBroadcasts(UUID uuid) {
        this.broadcasts.entrySet().removeIf(entry -> entry.getKey().uuid().equals(uuid));
    }

    void send(UUID uuid, Duration delay) {
        Duration actualDelay = delay.isNegative() ? Duration.ofSeconds(2) : delay;

        this.broadcasts.forEach((alertKey, broadcasts) -> {
            if (!alertKey.uuid().equals(uuid)) {
                return;
            }

            NoticeTextType type = alertKey.type();
            if (DELAYED_TYPES.contains(type)) {
                for (int i = 0; i < broadcasts.size(); i++) {
                    NoticeBroadcast<Viewer, Translation, ?> broadcast = broadcasts.get(i);
                    scheduler.runLater(broadcast::send, actualDelay.multipliedBy(i + 1));
                }
                return;
            }

            for (NoticeBroadcast<Viewer, Translation, ?> broadcast : broadcasts) {
                broadcast.send();
            }
        });

        clearBroadcasts(uuid);
    }

    private record AlertKey(UUID uuid, NoticeTextType type) {}
}
