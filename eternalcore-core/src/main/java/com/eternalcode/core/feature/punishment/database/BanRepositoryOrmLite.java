package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.ban.Ban;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.sql.SQLException;

@Repository
class BanRepositoryOrmLite extends AbstractExpiringPunishmentRepositoryOrmLite<Ban> implements BanRepository {

    @Inject
    private BanRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler, PunishmentMapper mapper) throws SQLException {
        super(databaseManager, scheduler, PunishmentKind.BAN, mapper::banToRow, mapper::rowToBan);
    }
}
