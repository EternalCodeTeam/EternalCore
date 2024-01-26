package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.injector.DependencyProvider;

class EternalCoreApiImpl implements EternalCoreApi {

    private final DependencyProvider dependencyProvider;

    public EternalCoreApiImpl(DependencyProvider dependencyProvider) {
        this.dependencyProvider = dependencyProvider;
    }

    @Override
    public AfkService getAfkService() {
        return this.dependencyProvider.getDependency(AfkService.class);
    }
}
