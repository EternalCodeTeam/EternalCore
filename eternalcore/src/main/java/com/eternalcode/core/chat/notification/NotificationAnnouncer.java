package com.eternalcode.core.chat.notification;

import com.eternalcode.core.viewer.Viewer;

public interface NotificationAnnouncer {

    void announce(Viewer viewer, Notification notification);

    void announce(Iterable<Viewer> viewers, Notification notification);

}
