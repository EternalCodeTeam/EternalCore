package com.eternalcode.core.telemetry.sentry;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import io.papermc.lib.PaperLib;
import io.sentry.Sentry;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Controller
class SentrySetup implements Subscriber {

    public static final String SENTRY_DSN = "https://4bf366d0d9f1da00162b9e629a80be52@o4505014505177088.ingest.sentry.io/4506093905051648";
    private final PluginConfiguration pluginConfiguration;

    @Inject
    SentrySetup(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize(JavaPlugin javaPlugin) {
        if (this.pluginConfiguration.useSentry) {
            Sentry.init(options -> {
                options.setDsn(SENTRY_DSN);
                options.setTracesSampleRate(1.0);

                Server server = javaPlugin.getServer();
                options.setTag("serverVersion", server.getVersion());
                options.setTag("serverSoftware", PaperLib.getEnvironment().getName());
            });

            Thread.setDefaultUncaughtExceptionHandler(new SentryExceptionHandler());

            javaPlugin.getLogger().info("Sentry initialized");
        }
    }

    @Subscribe(EternalShutdownEvent.class)
    public void onShutdown() {
        if (this.pluginConfiguration.useSentry) {
            Sentry.close();
        }
    }

}
