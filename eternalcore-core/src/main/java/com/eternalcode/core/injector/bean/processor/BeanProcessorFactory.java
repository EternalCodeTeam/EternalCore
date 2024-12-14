package com.eternalcode.core.injector.bean.processor;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.annotations.LiteCommandsAnnotations;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import java.lang.reflect.Method;

import dev.rollczi.litecommands.argument.ArgumentKey;
import dev.rollczi.litecommands.argument.resolver.MultipleArgumentResolver;
import dev.rollczi.litecommands.context.ContextProvider;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.scope.Scope;
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
                    scheduler.runLater(runnable, delay);
                }
                else {
                    scheduler.timer(runnable, delay, period);
                }
            })
            .onProcess(Listener.class, (provider, listener, none) -> {
                try { //todo handle it
                    pluginManager.registerEvents(listener, plugin);
                } catch (NoClassDefFoundError error) {
                    error.printStackTrace();
                }
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
            .onProcess(Command.class, Object.class, (provider, command, none) -> {
                LiteCommandsAnnotations<?> commandsBuilder = provider.getDependency(LiteCommandsAnnotations.class);
                commandsBuilder.load(command);
            })
            .onProcess(RootCommand.class, Object.class, (provider, rootCommand, none) -> {
                LiteCommandsAnnotations<?> commandsBuilder = provider.getDependency(LiteCommandsAnnotations.class);
                commandsBuilder.load(rootCommand);
            })
            .onProcess(LiteArgument.class, MultipleArgumentResolver.class, (provider, multilevelArgument, liteArgument) -> {
                LiteCommandsBuilder<?, ?, ?> builder = provider.getDependency(LiteCommandsBuilder.class);

                builder.argument(liteArgument.type(), ArgumentKey.of(liteArgument.name()), multilevelArgument);
            })
            .onProcess(LiteHandler.class, ResultHandler.class, (provider, handler, liteHandler) -> {
                LiteCommandsBuilder<?, ?, ?> builder = provider.getDependency(LiteCommandsBuilder.class);

                builder.result(liteHandler.value(), handler);
            })
            .onProcess(LiteContextual.class, ContextProvider.class, (provider, contextProvider, liteContextual) -> {
                LiteCommandsBuilder<?, ?, ?> builder = provider.getDependency(LiteCommandsBuilder.class);

                builder.context(liteContextual.value(), contextProvider);
            })
            .onProcess(LiteCommandEditor.class, Editor.class, (provider, editor, liteCommandEditor) -> {
                LiteCommandsBuilder<?, ?, ?> builder = provider.getDependency(LiteCommandsBuilder.class);
                Scope scope = Scope.global();

                if (liteCommandEditor.command() != Object.class) {
                    scope = Scope.command(liteCommandEditor.command());
                }

                if (!liteCommandEditor.name().isEmpty()) {
                    scope = Scope.command(liteCommandEditor.name());
                }

                builder.editor(scope, editor);
            });
    }

}
