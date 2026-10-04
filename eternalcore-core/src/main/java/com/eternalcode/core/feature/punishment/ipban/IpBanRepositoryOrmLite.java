package com.eternalcode.core.feature.punishment.ipban;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.feature.punishment.PunishmentKind;
import com.eternalcode.core.feature.punishment.database.AbstractExpiringPunishmentRepositoryOrmLite;
import com.eternalcode.core.feature.punishment.database.PunishmentMapper;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.sql.SQLException;

@Repository
class IpBanRepositoryOrmLite extends AbstractExpiringPunishmentRepositoryOrmLite<IpBan> implements IpBanRepository {

    @Inject
    private IpBanRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler, PunishmentMapper mapper) throws SQLException {
        super(databaseManager, scheduler, PunishmentKind.IP_BAN, mapper::ipBanToRow, mapper::rowToIpBan);
    }
}
