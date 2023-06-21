package com.eternalcode.core.notice;

import com.eternalcode.core.viewer.Viewer;

interface PlatformBroadcaster {

    void announce(Viewer viewer, Notice notice);

    void announce(Viewer viewer, Notice.Part<?> notice);

}
