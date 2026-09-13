package com.eternalcode.core.feature.setslot;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Server;
import org.jetbrains.annotations.Blocking;

@Service
public class SetSlotService {

    private final Server server;
    private final SetSlotSaver setSlotSaver;

    @Inject
    public SetSlotService(Server server, SetSlotSaver setSlotSaver) {
        this.server = server;
        this.setSlotSaver = setSlotSaver;
    }

    @Blocking
    public void setCapacity(int slots){
        this.server.setMaxPlayers(slots);
        this.setSlotSaver.save();
    }
}

