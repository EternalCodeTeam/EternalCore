package com.eternalcode.core.teleport;

import com.eternalcode.core.user.User;

public class TeleportRequest {

    private final User to;
    private final User from;
    private final long expire;

    public TeleportRequest(User from, User to, long expire) {
        this.to = to;
        this.from = from;
        this.expire = expire;
    }

    public User getFrom() {
        return this.from;
    }

    public User getTo() {
        return this.to;
    }

    public long getExpire() {
        return expire;
    }
}
