package com.eternalcode.core.feature.msgtoggle;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.UUID;

@Repository
class MsgToggleRepositoryOrmLite extends AbstractRepositoryOrmLite implements MsgToggleRepository {

    @Inject
    private MsgToggleRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), MsgToggle.class);
    }

    @Override
    public boolean isToggledOff(UUID uuid) {
        this.selectSafe(MsgToggle.class, uuid).thenApply(optional -> optional.isEmpty())
    }

    @Override
    public void setToggledOff(UUID uuid, boolean toggledOff) {

    }

    @Override
    public void remove(UUID uuid) {

    }

    @Override
    public void removeAll() {

    }
}
