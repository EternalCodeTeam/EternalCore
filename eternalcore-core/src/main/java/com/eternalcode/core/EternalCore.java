package com.eternalcode.core;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.event.EternalCoreInitializeEvent;
import com.eternalcode.core.event.EternalCoreShutdownEvent;
import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.DependencyProcessors;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.injector.annotations.component.ConfigurationYml;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.injector.annotations.lite.LiteNativeArgument;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.scheduler.Scheduler;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.simple.MultilevelArgument;
import dev.rollczi.litecommands.command.root.RootRoute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.handle.Handler;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.lang.annotation.Annotation;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

class EternalCore implements EternalCoreApi {

    private final DependencyInjector injector;

    public EternalCore(Plugin plugin) {
        this.softwareCheck(plugin.getLogger());

        Stopwatch started = Stopwatch.createStarted();
        EternalCoreApiProvider.initialize(this);

        Server server = plugin.getServer();
        PluginManager pluginManager = server.getPluginManager();

        DependencyProcessors processors = new DependencyProcessors()
            .on(BeanSetup.class)
            .on(Service.class)
            .on(Runnable.class, Task.class, (dependencyProvider, task) -> {
                Scheduler scheduler = dependencyProvider.getDependency(Scheduler.class);
                Task taskAnnotation = this.getAnnotation(task, Task.class);

                ChronoUnit unit = taskAnnotation.unit().toChronoUnit();
                Duration delay = Duration.of(taskAnnotation.delay(), unit);
                Duration period = Duration.of(taskAnnotation.period(), unit);

                if (period.isZero()) {
                    scheduler.laterSync(task, delay);
                }
                else {
                    scheduler.timerSync(task, delay, period);
                }
            })
            .on(Listener.class, Controller.class, listener -> pluginManager.registerEvents(listener, plugin))
            .on(Subscriber.class, Controller.class, (provider, instance) -> provider.getDependency(Publisher.class).subscribe(instance))
            .on(ReloadableConfig.class, ConfigurationYml.class, (provider, config) -> provider.getDependency(ConfigurationManager.class).load(config))
            .on(Route.class, (provider, commandInstance) -> provider.getDependency(LiteCommandsBuilder.class).commandInstance(commandInstance))
            .on(RootRoute.class, (provider, commandInstance) -> provider.getDependency(LiteCommandsBuilder.class).commandInstance(commandInstance))
            .on(MultilevelArgument.class, LiteArgument.class, (provider, argument) -> {
                LiteCommandsBuilder<CommandSender> builder = provider.getDependency(LiteCommandsBuilder.class);
                LiteArgument annotation = this.getAnnotation(argument, LiteArgument.class);

                builder.argumentMultilevel(annotation.type(), annotation.name(), argument);
            })
            .on(Argument.class, LiteNativeArgument.class, (provider, argument) -> {
                LiteCommandsBuilder<CommandSender> builder = provider.getDependency(LiteCommandsBuilder.class);
                LiteNativeArgument annotation = this.getAnnotation(argument, LiteNativeArgument.class);

                builder.argument(annotation.annotation(), annotation.type(), argument);
            })
            .on(Handler.class, LiteHandler.class, (provider, handler) -> {
                LiteCommandsBuilder<CommandSender> builder = provider.getDependency(LiteCommandsBuilder.class);
                LiteHandler annotation = this.getAnnotation(handler, LiteHandler.class);

                builder.resultHandler(annotation.value(), handler);
            });

        this.injector = new DependencyInjector(processors)
            .registerSelf()
            .register(Server.class, () -> server)
            .register((Class<Plugin>) plugin.getClass(), () -> plugin)
            .register(Logger.class, () -> plugin.getLogger())
            .register(File.class, () -> plugin.getDataFolder())
            .scan(scanner -> scanner.packages(EternalCore.class.getPackageName()))
            .initializeAll();

        Publisher publisher = this.injector.getDependency(Publisher.class);
        publisher.publish(new EternalCoreInitializeEvent());

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        plugin.getLogger().info("Successfully loaded EternalCore in " + millis + "ms");
    }

    public void disable() {
        Publisher publisher = this.injector.getDependency(Publisher.class);
        publisher.publish(new EternalCoreShutdownEvent());

        EternalCoreApiProvider.deinitialize();
    }

    private void softwareCheck(Logger logger) {
        Environment environment = PaperLib.getEnvironment();

        if (!environment.isSpigot()) {
            logger.warning("Your server running on unsupported software, use spigot/paper minecraft software and other spigot/paper 1.19x forks");
            logger.warning("We recommend using paper, download paper from https://papermc.io/downloads");
            logger.warning("WARRING: Supported minecraft version is 1.17-1.19x");
            return;
        }

        if (!environment.isVersion(17)) {
            logger.warning("EternalCore no longer supports your version, be aware that there may be bugs!");
            return;
        }

        logger.info("Your server running on supported software, congratulations!");
        logger.info("Server version: " + environment.getMinecraftVersion());
    }

    private <A extends Annotation> A getAnnotation(Object instance, Class<A> annotationClass) {
        Class<?> clazz = instance.getClass();
        A annotation = clazz.getAnnotation(annotationClass);

        if (annotation == null) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " must be annotated with @" + annotationClass.getName());
        }

        return annotation;
    }

}
