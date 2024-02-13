package com.eternalcode.core.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JailController implements Listener {

    private  final JailService jailService;

    public JailController(JailService jailService) {
        this.jailService = jailService;
    }

    @EventHandler
    public void onJailRelease(JailReleaseEvent event) {
        if (event.isCancelled()) {
            return;
        }

    }

    @EventHandler
    public void onJailDetain(JailDetainEvent event) {
        if (event.isCancelled()) {
            return;
        }


    }




}
