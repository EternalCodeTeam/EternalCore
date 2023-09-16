package com.eternalcode.core.injector.scan;

import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.component.BeanSetup;
import com.eternalcode.core.injector.annotations.component.Component;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.injector.annotations.lite.LiteNativeArgument;
import dev.rollczi.litecommands.command.root.RootRoute;
import dev.rollczi.litecommands.command.route.Route;

public final class DependencyScannerFactory {

    private DependencyScannerFactory() {
    }

    public static DependencyScanner createDefault(DependencyInjector injector) {
        return new DependencyScanner(injector)
            .onAnnotations(
                Component.class,
                Service.class,
                Repository.class,
                Task.class,
                Controller.class,
                ConfigurationFile.class,
                BeanSetup.class,

                Route.class,
                RootRoute.class,
                LiteArgument.class,
                LiteNativeArgument.class,
                LiteHandler.class,
                LiteContextual.class
            );
    }


}
