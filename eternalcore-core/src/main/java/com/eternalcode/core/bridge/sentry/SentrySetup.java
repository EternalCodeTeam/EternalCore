package com.eternalcode.core.bridge.sentry;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import io.papermc.lib.PaperLib;
import io.sentry.Sentry;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

@BeanSetup
class SentrySetup implements Subscriber {

    private final PluginConfiguration config;

    @Inject
    SentrySetup(PluginConfiguration config) {
        this.config = config;
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize(Plugin plugin, Server server) {
        if (this.config.sentryEnabled) {
            Sentry.init(options -> {
                options.setDsn("https://4bf366d0d9f1da00162b9e629a80be52@o4505014505177088.ingest.us.sentry.io/4506093905051648");
                options.setTracesSampleRate(0.75);
                options.setRelease(plugin.getDescription().getVersion());
                options.setEnvironment(PaperLib.getEnvironment().getName());
                options.setTag("plugins", Arrays.stream(server.getPluginManager().getPlugins()).toList().toString());
                options.setTag("serverVersion", server.getVersion());
            });
        }
    }
}
