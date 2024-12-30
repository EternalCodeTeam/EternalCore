package com.eternalcode.core.injector.scan;

import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.component.Setup;
import com.eternalcode.core.injector.annotations.component.Component;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.injector.annotations.component.Task;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.command.RootCommand;

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
                Setup.class,

                Command.class,
                RootCommand.class,
                LiteArgument.class,
                LiteHandler.class,
                LiteContextual.class,
                LiteCommandEditor.class
            );
    }


}
