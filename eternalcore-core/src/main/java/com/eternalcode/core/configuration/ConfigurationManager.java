package com.eternalcode.core.configuration;

import com.eternalcode.core.configuration.serializer.LanguageSerializer;
import com.eternalcode.core.configuration.transformer.PositionTransformer;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.okaeri.MultificationNoticeSerializer;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

@Service
public class ConfigurationManager {

    private final Set<OkaeriConfig> configs = new HashSet<>();

    private final Publisher publisher;
    private final NoticeResolverRegistry resolverRegistry;
    private final File dataFolder;

    @Inject
    public ConfigurationManager(
        NoticeResolverRegistry resolverRegistry,
        Publisher publisher,
        File dataFolder
    ) {
        this.resolverRegistry = resolverRegistry;
        this.publisher = publisher;
        this.dataFolder = dataFolder;
    }

    public <T extends OkaeriConfig & EternalConfigurationFile> T load(T config) {
        YamlSnakeYamlConfigurer yamlConfigurer = new YamlSnakeYamlConfigurer(this.createYaml());

        OkaeriSerdesPack serdesPack = registry -> {
            registry.register(new PositionTransformer());
            registry.register(new LanguageSerializer());
            registry.register(new MultificationNoticeSerializer(this.resolverRegistry));
            registry.register(new SerdesCommons());

            this.publisher.publish(new ConfigurationSerdesSetupEvent(registry));
        };

        config.withConfigurer(yamlConfigurer)
            .withSerdesPack(serdesPack)
            .withBindFile(config.getConfigFile(dataFolder))
            .withRemoveOrphans(true)
            .saveDefaults()
            .load(true);

        this.configs.add(config);
        return config;
    }

    private Yaml createYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(loaderOptions);

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        dumperOptions.setIndent(2);
        dumperOptions.setSplitLines(false);

        Representer representer = new CustomSnakeYamlRepresenter(dumperOptions);
        Resolver resolver = new Resolver();

        return new Yaml(constructor, representer, dumperOptions, loaderOptions, resolver);
    }

    public void reload() {
        for (OkaeriConfig config : this.configs) {
            config.load();
        }
    }

    public void save(OkaeriConfig config) {
        config.save();
    }

    public Set<OkaeriConfig> getConfigs() {
        return Collections.unmodifiableSet(this.configs);
    }

    public File getDataFolder() {
        return this.dataFolder;
    }
}
