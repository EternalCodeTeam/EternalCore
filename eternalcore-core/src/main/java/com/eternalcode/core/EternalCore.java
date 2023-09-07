package com.eternalcode.core;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.bean.BeanCandidate;
import com.eternalcode.core.injector.bean.BeanFactory;
import com.eternalcode.core.injector.bean.BeanHolder;
import com.eternalcode.core.injector.bean.LazyFieldBeanCandidate;
import com.eternalcode.core.injector.bean.processor.BeanProcessor;
import com.eternalcode.core.injector.bean.processor.BeanProcessorFactory;
import com.eternalcode.core.injector.scan.DependencyScanner;
import com.eternalcode.core.injector.scan.DependencyScannerFactory;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import com.eternalcode.core.publish.Publisher;
import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

class EternalCore implements EternalCoreApi {

    private final EternalCoreEnvironment eternalCoreEnvironment;
    private final Publisher publisher;

    public EternalCore(Plugin plugin) {
        this.eternalCoreEnvironment = new EternalCoreEnvironment(this, plugin.getLogger());

        BeanProcessor beanProcessor = BeanProcessorFactory.defaultProcessors(plugin);
        BeanFactory beanFactory = new BeanFactory(beanProcessor)
            .withCandidateSelf()
            .addCandidate(Plugin.class,                () -> plugin)
            .addCandidate(Server.class,                () -> plugin.getServer())
            .addCandidate(Logger.class,                () -> plugin.getLogger())
            .addCandidate(PluginDescriptionFile.class, () -> plugin.getDescription())
            .addCandidate(File.class,                  () -> plugin.getDataFolder())
            .addCandidate(PluginManager.class,         () -> plugin.getServer().getPluginManager());

        DependencyInjector dependencyInjector = new DependencyInjector(beanFactory);
        DependencyScanner scanner = DependencyScannerFactory.createDefault(dependencyInjector);

        beanFactory.addCandidate(DependencyInjector.class, () -> dependencyInjector);

        for (BeanCandidate beanCandidate : scanner.scan(EternalCore.class.getPackage())) {
            beanFactory.addCandidate(beanCandidate);
        }

        this.loadConfigContextual(beanFactory);

        beanFactory.initializeCandidates();

        this.publisher = beanFactory.getDependency(Publisher.class);

        this.eternalCoreEnvironment.initialize();
        this.publisher.publish(new EternalInitializeEvent());
    }

    public void disable() {
        this.publisher.publish(new EternalShutdownEvent());
        this.eternalCoreEnvironment.shutdown();
    }

    private void loadConfigContextual(BeanFactory beanFactory) {
        List<BeanHolder<ReloadableConfig>> beans = beanFactory
            .initializeCandidates(ReloadableConfig.class)
            .getBeans(ReloadableConfig.class);

        for (BeanHolder<ReloadableConfig> bean : beans) {
            ReloadableConfig config = bean.get();

            for (Field field : config.getClass().getDeclaredFields()) {
                if (!field.getType().isAnnotationPresent(Contextual.class)) {
                    continue;
                }

                beanFactory.addCandidate(new LazyFieldBeanCandidate(config, field));
            }
        }
    }

}
