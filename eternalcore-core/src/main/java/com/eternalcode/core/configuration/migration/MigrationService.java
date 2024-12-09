package com.eternalcode.core.configuration.migration;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.ReflectUtil;
import java.lang.reflect.Field;
import net.dzikoysk.cdn.entity.Contextual;

@Service
class MigrationService {

    public <T extends ReloadableConfig> boolean migrate(T config) {
        return reflectMigrate(config);
    }

    private <T> boolean reflectMigrate(T config) {
        boolean isMigrated = false;

        for (Field declaredField : ReflectUtil.getAllSuperFields(config.getClass())) {
            Class<?> fieldType = declaredField.getType();

            if (Migration.class.isAssignableFrom(fieldType)) {
                Migration migration = ReflectUtil.getFieldValue(declaredField, config);
                boolean wasMigrationSuccessful = migration.migrate();
                isMigrated |= wasMigrationSuccessful;
            }

            if (fieldType.isAnnotationPresent(Contextual.class)) {
                Object fieldValue = ReflectUtil.getFieldValue(declaredField, config);
                isMigrated |= reflectMigrate(fieldValue);
            }
        }

        return isMigrated;
    }

}
