package com.eternalcode.core.feature.essentials.alert;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.EternalCoreBroadcastImpl;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;
import net.kyori.adventure.text.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
class AlertManager {
    private final ConcurrentHashMap<NoticeTextType, List<EternalCoreBroadcastImpl<Viewer, Translation, ?>>> broadcasts = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Inject
    AlertManager() {
    }

    void addBroadcast(NoticeTextType type, EternalCoreBroadcastImpl<Viewer, Translation, ?> broadcast) {
        this.broadcasts.computeIfAbsent(type, k -> new ArrayList<>()).add(broadcast);
    }

    boolean removeBroadcastWithType(NoticeTextType type) {
        if (this.broadcasts.containsKey(type)) {
            this.broadcasts.remove(type);
            return true;
        }
        return false;
    }

    boolean removeLatestBroadcastWithType(NoticeTextType type) {
        List<EternalCoreBroadcastImpl<Viewer, Translation, ?>> broadcastList = this.broadcasts.get(type);
        if (broadcastList != null && !broadcastList.isEmpty()) {
            broadcastList.remove(broadcastList.size() - 1);
            if (broadcastList.isEmpty()) {
                return this.removeBroadcastWithType(type);
            }
        }
        return false;
    }

    void clearBroadcasts() {
        this.broadcasts.clear();
    }

    void send() {
        this.broadcasts.forEach((type, broadcastList) -> {
            if (type == NoticeTextType.TITLE || type == NoticeTextType.SUBTITLE) {
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
        clearBroadcasts();
    }
}
