package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class AbstractConfigWithResource implements ConfigWithResource {

    @Exclude
    private Resource resource;

    @Contract("null -> fail")
    protected AbstractConfigWithResource(Resource resource) {
        Validate.notNull(resource);
        this.resource = resource;
    }

    @Contract("null, null -> fail")
    protected AbstractConfigWithResource(File folder, String child) {
        Validate.notNull(folder);
        Validate.notNull(child);
        this.resource = Source.of(new File(folder, child));
    }

    @Exclude
    @Override @NotNull
    public Resource getResource() {
        return this.resource;
    }

    @Exclude
    @Override @Contract("null -> fail")
    public void setResource(Resource resource) {
        Validate.notNull(resource);
        this.resource = resource;
    }

}
