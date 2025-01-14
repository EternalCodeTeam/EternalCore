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
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), MsgToggleWrapper.class);
    }

    @Override
    public boolean isToggledOff(UUID uuid) {
//        this.selectSafe(MsgToggle.class, uuid).thenApply(msgToggle -> {
//            msgToggle.isEmpty();
//        });
        return false;
    }

    @Override
    public void setToggledOff(UUID uuid, boolean toggledOff) {
        this.save(MsgToggle.class, new MsgToggle(uuid, false));
    }

    @Override
    public void remove(UUID uuid) {

    }

    @Override
    public void removeAll() {

    }
}
