package com.eternalcode.core.feature.jail;

import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.eternalcode.core.scheduler.Scheduler;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import net.kyori.option.Option;
import panda.std.reactive.Completable;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class JailRepositoryOrmLite extends AbstractRepositoryOrmLite implements JailRepository {

    @Inject
    private JailRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PrisonerWrapper.class);
    }

    @Override
    public Completable<Optional<Prisoner>> getPrisoner(UUID uuid) {
        return this.selectSafe(PrisonerWrapper.class, uuid)
            .thenApply(optional -> optional.map(prisonerWrapper -> prisonerWrapper.toPrisoner()));
    }

    @Override
    public void deletePrisoner(UUID uuid) {
        this.deleteById(PrisonerWrapper.class, uuid);
    }

    @Override
    public void editPrisoner(Prisoner prisoner) {
        this.save(PrisonerWrapper.class, PrisonerWrapper.from(prisoner));
    }

    @Override
    public void deleteAllPrisoners() {
        this.delete(PrisonerWrapper.class, new PrisonerWrapper());
    }

    @Override
    public Completable<List<Prisoner>> getAllPrisoners() {
        return this.selectAll(PrisonerWrapper.class)
            .thenApply(prisonerWrappers -> prisonerWrappers.stream()
                .map(PrisonerWrapper::toPrisoner)
                .toList());
    }

    @Override
    public void savePrisoner(Prisoner prisoner) {
        this.save(PrisonerWrapper.class, PrisonerWrapper.from(prisoner));
    }

    @DatabaseTable(tableName = "eternal_core_prisoners")
    static class PrisonerWrapper {

        @DatabaseField(columnName = "id", id = true)
        private UUID uuid;

        @DatabaseField(columnName = "reason")
        private String reason;

        @DatabaseField(columnName = "detained_at")
        private Instant detainedAt;

        @DatabaseField(columnName = "duration")
        private Duration duration;

        @DatabaseField(columnName = "detained_by")
        private UUID detainedBy;

        PrisonerWrapper() {
        }

        PrisonerWrapper(UUID uuid, String reason, Instant detainedAt, Duration duration, UUID detainedBy) {
            this.uuid = uuid;
            this.reason = reason;
            this.detainedAt = detainedAt;
            this.duration = duration;
            this.detainedBy = detainedBy;
        }

        Prisoner toPrisoner() {
            return new Prisoner(this.uuid, this.reason, this.detainedAt, this.duration, this.detainedBy);
        }

        static PrisonerWrapper from(Prisoner prisoner) {
            return new PrisonerWrapper(prisoner.getUuid(), prisoner.getReason(), prisoner.getDetainedAt(), prisoner.getDuration(), prisoner.getDetainedBy());
        }
    }

}
