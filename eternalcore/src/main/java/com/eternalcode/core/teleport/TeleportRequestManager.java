package com.eternalcode.core.teleport;

import com.eternalcode.core.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TeleportRequestManager {

    private List<TeleportRequest> teleportRequest = new ArrayList<>();

    public void createRequest(User from, User to) {
        long expire = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(80);

        this.teleportRequest.add(new TeleportRequest(from, to, expire));
    }

    public void removeRequest(TeleportRequest request) {
        this.teleportRequest.remove(request);
    }

    public List<TeleportRequest> getRequests(User user) {
        return this.teleportRequest.stream()
            .filter(request -> request.getTo().equals(user))
            .filter(request -> request.getExpire() > System.currentTimeMillis())
            .collect(Collectors.toList());
    }

    public Collection<TeleportRequest> getTeleportRequests() {
        return Collections.unmodifiableCollection(this.teleportRequest);
    }

}
