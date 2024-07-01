package com.eternalcode.core.bridge.sentry;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.DependencyInjectorException;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.injector.bean.BeanException;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.google.common.collect.ImmutableList;
import dev.rollczi.litecommands.LiteCommandsException;
import io.papermc.lib.PaperLib;
import io.sentry.Sentry;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.net.MalformedURLException;
import java.nio.file.FileSystemException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@BeanSetup
class SentrySetup implements Subscriber {

    private static final List<Class<? extends Throwable>> LOADER_OR_INJECTOR_EXCEPTIONS = List.of(
        ReflectiveOperationException.class,
        FileSystemException.class,
        SAXException.class,
        BeanException.class,
        NoClassDefFoundError.class,
        MalformedURLException.class,
        ParserConfigurationException.class,
        DependencyInjectorException.class
    );

    private static final List<Class<? extends Throwable>> DATABASE_EXCEPTIONS = ImmutableList.of(
        SQLException.class,
        ExecutionException.class
    );

    private static final List<Class<? extends Throwable>> LITECOMMANDS_EXCEPTIONS = ImmutableList.of(
        LiteCommandsException.class,
        DateTimeParseException.class
    );


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
                options.setBeforeSend((event, hint) -> {
                    Throwable throwable = event.getThrowable();

                    if (throwable != null) {
                        if (LOADER_OR_INJECTOR_EXCEPTIONS.stream().anyMatch(exception -> exception.isInstance(throwable))) {
                            event.setFingerprints(List.of("loader_or_injector"));
                        } else if (DATABASE_EXCEPTIONS.stream().anyMatch(exception -> exception.isInstance(throwable))) {
                            event.setFingerprints(List.of("database"));
                        } else if (LITECOMMANDS_EXCEPTIONS.stream().anyMatch(exception -> exception.isInstance(throwable))) {
                            event.setFingerprints(List.of("litecommands"));
                        }
                    }

                    return event;
                });
            });
        }
    }
}
