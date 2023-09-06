package com.eternalcode.core.bridge;

import com.eternalcode.core.event.EternalCoreInitializeEvent;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;

@Controller
class BridgeManagerSetup implements Subscriber {

    @Subscribe
    public void onEnable(EternalCoreInitializeEvent event, BridgeManager bridgeManager) {
        bridgeManager.init();
    }

}
