package com.eternalcode.core.database.jooq;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.DatabaseSettings;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.Setup;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

@Setup
class JooqSetup {

    @Bean
    DSLContext dslContext(DatabaseManager databaseManager, DatabaseSettings databaseSettings) {
        SQLDialect dialect = JooqDialectMapper.map(databaseSettings.databaseType());
        return DSL.using(databaseManager.dataSource(), dialect);
    }
}
