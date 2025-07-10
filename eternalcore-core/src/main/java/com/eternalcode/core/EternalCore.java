package com.eternalcode.core;

import com.eternalcode.core.compatibility.CompatibilityService;
import com.eternalcode.core.configuration.EternalConfigurationFile;
import com.eternalcode.core.configuration.compatibility.ConfigurationCompatibilityV21_2;
import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.component.Component;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.injector.bean.BeanCandidate;
import com.eternalcode.core.injector.bean.BeanFactory;
import com.eternalcode.core.injector.bean.BeanCandidatePriorityProvider;
import com.eternalcode.core.injector.bean.processor.BeanProcessor;
import com.eternalcode.core.injector.bean.processor.BeanProcessorFactory;
import com.eternalcode.core.injector.scan.DependencyScanner;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.logging.Logger;

class EternalCore {

    private final EternalCoreEnvironment eternalCoreEnvironment;
    private final Publisher publisher;

    public EternalCore(Plugin plugin) {
        this.eternalCoreEnvironment = new EternalCoreEnvironment(plugin.getLogger());

        CompatibilityService compatibilityService = new CompatibilityService();
        BeanProcessor beanProcessor = BeanProcessorFactory.defaultProcessors(plugin);
        BeanFactory beanFactory = new BeanFactory(beanProcessor)
            .withCandidateSelf()
            .addCandidate(Plugin.class,                () -> plugin)
            .addCandidate(Server.class,                () -> plugin.getServer())
            .addCandidate(Logger.class,                () -> plugin.getLogger())
            .addCandidate(PluginDescriptionFile.class, () -> plugin.getDescription())
            .addCandidate(File.class,                  () -> plugin.getDataFolder())
            .addCandidate(PluginManager.class,         () -> plugin.getServer().getPluginManager())
            .priorityProvider(new BeanCandidatePriorityProvider());

        DependencyInjector injector = new DependencyInjector(beanFactory);
        DependencyScanner scanner = new DependencyScanner(injector)
            .includeType(type -> compatibilityService.isCompatible(type))
            .includeAnnotations(
                Component.class,
                Service.class,
                Repository.class,
                Task.class,
                Controller.class,
                ConfigurationFile.class,
                Setup.class,

                Command.class,
                RootCommand.class,
                LiteArgument.class,
                LiteHandler.class,
                LiteContextual.class,
                LiteCommandEditor.class
            );

        beanFactory.addCandidate(DependencyInjector.class, () -> injector);

        for (BeanCandidate beanCandidate : scanner.scan(EternalCore.class.getPackage())) {
            beanFactory.addCandidate(beanCandidate);
        }

        beanFactory.initializeCandidates(ConfigurationCompatibilityV21_2.class);
        beanFactory.initializeCandidates(EternalConfigurationFile.class);
        beanFactory.initializeCandidates();

        this.publisher = beanFactory.getDependency(Publisher.class);

        EternalCoreApiProvider.initialize(new EternalCoreApiImpl(beanFactory));
        this.eternalCoreEnvironment.initialize();
        this.publisher.publish(new EternalInitializeEvent());
    }

    public void disable() {
        this.publisher.publish(new EternalShutdownEvent());
        EternalCoreApiProvider.deinitialize();
    }

}
