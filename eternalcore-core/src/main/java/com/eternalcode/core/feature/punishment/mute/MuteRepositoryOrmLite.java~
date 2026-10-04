package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.mute.Mute;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.sql.SQLException;

@Repository
class MuteRepositoryOrmLite extends AbstractExpiringPunishmentRepositoryOrmLite<Mute> implements MuteRepository {

    @Inject
    private MuteRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler, PunishmentMapper mapper) throws SQLException {
        super(databaseManager, scheduler, PunishmentKind.MUTE, mapper::muteToRow, mapper::rowToMute);
    }
}
