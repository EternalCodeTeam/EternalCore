package com.eternalcode.core.feature.afk;

public class AfkTask implements Runnable {

    private final AfkService afkService;

    public AfkTask(AfkService afkService) {
        this.afkService = afkService;
    }

    @Override
    public void run() {
        this.afkService.checkLastMovement();
    }
}
