package com.eternalcode.core.feature.essentials.alert;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.EternalCoreBroadcastImpl;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;
import net.kyori.adventure.text.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
class AlertManager {
    //private final Map<UUID, Map<NoticeTextType, List<EternalCoreBroadcastImpl<Viewer, Translation, ?>>>> broadcasts = new HashMap<>();
    private final Map<AlertKey, List<EternalCoreBroadcastImpl<Viewer, Translation, ?>>> broadcasts = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Inject
    AlertManager() {
    }

    void addBroadcast(UUID uuid, NoticeTextType type, EternalCoreBroadcastImpl<Viewer, Translation, ?> broadcast) {
        this.broadcasts.computeIfAbsent(new AlertKey(uuid, type), k -> new ArrayList<>()).add(broadcast);
    }

    boolean removeBroadcastWithType(UUID uuid, NoticeTextType type) {
        if (this.broadcasts.containsKey(new AlertKey(uuid, type))) {
            this.broadcasts.remove(new AlertKey(uuid, type));
            return true;
        }
        return false;
    }

    boolean removeLatestBroadcastWithType(UUID uuid, NoticeTextType type) {
        List<EternalCoreBroadcastImpl<Viewer, Translation, ?>> broadcastList = this.broadcasts.get(new AlertKey(uuid, type));
        if (broadcastList != null && !broadcastList.isEmpty()) {
            broadcastList.remove(broadcastList.size() - 1);
            if (broadcastList.isEmpty()) {
                return this.removeBroadcastWithType(uuid, type);
            }
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
                    scheduler.schedule(broadcast::send, i * 2L, TimeUnit.SECONDS);
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
