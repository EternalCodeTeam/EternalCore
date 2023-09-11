package com.eternalcode.core.injector.bean.processor;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.injector.annotations.lite.LiteNativeArgument;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.scheduler.Scheduler;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.simple.MultilevelArgument;
import dev.rollczi.litecommands.command.root.RootRoute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.contextual.Contextual;
import dev.rollczi.litecommands.factory.CommandEditor;
import dev.rollczi.litecommands.handle.Handler;
import java.lang.reflect.Method;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public final class BeanProcessorFactory {

    private BeanProcessorFactory() {
    }

    public static BeanProcessor defaultProcessors(Plugin plugin) {
        Server server = plugin.getServer();
        PluginManager pluginManager = server.getPluginManager();

        return new BeanProcessor()
            .onProcess(Task.class, Runnable.class, (provider, runnable, task) -> {
                Scheduler scheduler = provider.getDependency(Scheduler.class);

                ChronoUnit unit = task.unit().toChronoUnit();
                Duration delay = Duration.of(task.delay(), unit);
                Duration period = Duration.of(task.period(), unit);

                if (period.isZero()) {
                    scheduler.laterSync(runnable, delay);
                }
                else {
                    scheduler.timerSync(runnable, delay, period);
                }
            })
            .onProcess(Listener.class, (provider, listener, none) -> {
                pluginManager.registerEvents(listener, plugin);
            })
            .onProcess(Subscriber.class, (provider, potentialSubscriber, none) -> {
                Publisher publisher = provider.getDependency(Publisher.class);
                publisher.subscribe(potentialSubscriber);
            })
            .onProcess(Object.class, (dependencyProvider, instance, none) -> {
                if (instance instanceof Subscriber) {
                    return;
                }

                for (Method method : instance.getClass().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Subscribe.class)) {
                        throw new IllegalStateException("Missing 'implements Subscriber' in declaration of class " + instance.getClass());
                    }
                }
            })
            .onProcess(ReloadableConfig.class, (provider, config, configurationFile) -> {
                ConfigurationManager configurationManager = provider.getDependency(ConfigurationManager.class);
                configurationManager.load(config);
            })
            .onProcess(Route.class, Object.class, (provider, route, none) -> {
                LiteCommandsBuilder<?> commandsBuilder = provider.getDependency(LiteCommandsBuilder.class);
                commandsBuilder.commandInstance(route);
            })
            .onProcess(RootRoute.class, Object.class, (provider, route, none) -> {
                LiteCommandsBuilder<?> commandsBuilder = provider.getDependency(LiteCommandsBuilder.class);
                commandsBuilder.commandInstance(route);
            })
            .onProcess(LiteArgument.class, MultilevelArgument.class, (provider, multilevelArgument, liteArgument) -> {
                LiteCommandsBuilder<?> builder = provider.getDependency(LiteCommandsBuilder.class);

                builder.argumentMultilevel(liteArgument.type(), liteArgument.name(), multilevelArgument);
            })
            .onProcess(LiteNativeArgument.class, Argument.class, (provider, argument, nativeArgument) -> {
                LiteCommandsBuilder<?> builder = provider.getDependency(LiteCommandsBuilder.class);

                builder.argument(nativeArgument.annotation(), nativeArgument.type(), argument);
            })
            .onProcess(LiteHandler.class, Handler.class, (provider, handler, liteHandler) -> {
                LiteCommandsBuilder<?> builder = provider.getDependency(LiteCommandsBuilder.class);

                builder.resultHandler(liteHandler.value(), handler);
            })
            .onProcess(LiteContextual.class, Contextual.class, (provider, contextual, liteContextual) -> {
                LiteCommandsBuilder<?> builder = provider.getDependency(LiteCommandsBuilder.class);

                builder.contextualBind(liteContextual.value(), contextual);
            })
            .onProcess(LiteCommandEditor.class, CommandEditor.class, (provider, editor, liteCommandEditor) -> {
                LiteCommandsBuilder<?> builder = provider.getDependency(LiteCommandsBuilder.class);

                if (liteCommandEditor.command() != Object.class) {
                    builder.commandEditor(liteCommandEditor.command(), editor);
                    return;
                }

                if (!liteCommandEditor.name().isEmpty()) {
                    builder.commandEditor(liteCommandEditor.name(), editor);
                    return;
                }

                builder.commandGlobalEditor(editor);
            });
    }

}
