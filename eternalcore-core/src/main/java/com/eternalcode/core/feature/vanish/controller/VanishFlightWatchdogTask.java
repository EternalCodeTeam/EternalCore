package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;
import java.util.concurrent.TimeUnit;

@Task(period = 5, delay = 5, unit = TimeUnit.SECONDS)
class VanishFlightWatchdogTask implements Runnable {

    private final VanishService vanishService;
    private final VanishFlightController vanishFlightController;

    @Inject
    VanishFlightWatchdogTask(VanishService vanishService, VanishFlightController vanishFlightController) {
        this.vanishService = vanishService;
        this.vanishFlightController = vanishFlightController;
    }

    @Override
    public void run() {
        this.vanishFlightController.synchronize(this.vanishService);
    }
}
