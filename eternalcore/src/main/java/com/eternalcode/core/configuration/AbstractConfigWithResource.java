package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class AbstractConfigWithResource implements ConfigWithResource {

    @Exclude
    private Resource resource;

    protected AbstractConfigWithResource(Resource resource) {
        this.resource = resource;
    }

    protected AbstractConfigWithResource(File folder, String child) {
        this.resource = Source.of(new File(folder, child));
    }

    @Exclude
    @Override @NotNull
    public Resource getResource() {
        return this.resource;
    }

    @Exclude
    public void setResource(Resource resource) {
        this.resource = resource;
    }

}
