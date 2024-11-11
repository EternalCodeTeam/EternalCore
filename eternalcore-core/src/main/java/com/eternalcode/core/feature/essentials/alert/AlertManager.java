package com.eternalcode.core.feature.essentials.alert;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.EternalCoreBroadcastImpl;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
class AlertManager {

    private final Map<AlertKey, List<EternalCoreBroadcastImpl<Viewer, Translation, ?>>> broadcasts = new HashMap<>();
    private final Scheduler scheduler;

    @Inject
    AlertManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    void addBroadcast(UUID uuid, NoticeTextType type, EternalCoreBroadcastImpl<Viewer, Translation, ?> broadcast) {
        this.broadcasts.computeIfAbsent(new AlertKey(uuid, type), k -> new ArrayList<>()).add(broadcast);
    }

    boolean removeBroadcastWithType(UUID uuid, NoticeTextType type) {
        return !this.broadcasts.remove(new AlertKey(uuid, type)).isEmpty();
    }

    boolean removeLatestBroadcastWithType(UUID uuid, NoticeTextType type) {
        AlertKey key = new AlertKey(uuid, type);
        List<EternalCoreBroadcastImpl<Viewer, Translation, ?>> broadcastList = this.broadcasts.get(key);

        if (broadcastList != null && !broadcastList.isEmpty()) {
            broadcastList.removeLast();
            if (broadcastList.isEmpty()) {
                this.removeBroadcastWithType(uuid, type);
            }
            return true;
        }
        return false;
    }

    void clearBroadcasts(UUID uuid) {
        this.broadcasts.entrySet().removeIf(entry -> entry.getKey().uuid().equals(uuid));
    }

    void send(UUID uuid) {
        this.broadcasts.forEach((alertKey, broadcastList) -> {
            if (!alertKey.uuid().equals(uuid)) {
                return;
            }

            NoticeTextType type = alertKey.type();
            if (type == NoticeTextType.TITLE || type == NoticeTextType.SUBTITLE || type == NoticeTextType.ACTIONBAR) {
                for (int i = 0; i < broadcastList.size(); i++) {
                    EternalCoreBroadcastImpl<Viewer, Translation, ?> broadcast = broadcastList.get(i);
                    scheduler.runLater(broadcast::send, Duration.ofSeconds(i * 2L));
                }
            } else {
                for (EternalCoreBroadcastImpl<Viewer, Translation, ?> broadcast : broadcastList) {
                    broadcast.send();
                }
            }
        });
        clearBroadcasts(uuid);
    }

    private record AlertKey(UUID uuid, NoticeTextType type) {}
}
