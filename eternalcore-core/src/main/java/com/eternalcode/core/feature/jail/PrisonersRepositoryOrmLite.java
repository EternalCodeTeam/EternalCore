package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
class PrisonersRepositoryOrmLite extends AbstractRepositoryOrmLite implements PrisonersRepository {

    @Inject
    private PrisonersRepositoryOrmLite(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), PrisonerWrapper.class);
    }

    @Override
    public CompletableFuture<Optional<Prisoner>> getPrisoner(UUID uuid) {
        return this.selectSafe(PrisonerWrapper.class, uuid)
            .thenApply(optional -> optional.map(prisonerWrapper -> prisonerWrapper.toPrisoner()));
    }

    @Override
    public CompletableFuture<Set<Prisoner>> getPrisoners() {
        return this.selectAll(PrisonerWrapper.class)
            .thenApply(prisonerWrappers -> prisonerWrappers.stream()
                .map(PrisonerWrapper::toPrisoner)
                .collect(Collectors.toSet()));
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
    public CompletableFuture<List<Prisoner>> getAllPrisoners() {
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


        @DatabaseField(columnName = "detained_at", dataType = DataType.SERIALIZABLE)
        private Instant detainedAt;

        @DatabaseField(columnName = "duration", dataType = DataType.SERIALIZABLE)
        private Duration duration;

        @DatabaseField(columnName = "detained_by")
        private String detainedBy;

        PrisonerWrapper() {
        }

        PrisonerWrapper(UUID uuid, Instant detainedAt, Duration duration, String detainedBy) {
            this.uuid = uuid;
            this.detainedAt = detainedAt;
            this.duration = duration;
            this.detainedBy = detainedBy;
        }

        Prisoner toPrisoner() {
            return new Prisoner(this.uuid, this.detainedAt, this.duration, this.detainedBy);
        }

        static PrisonerWrapper from(Prisoner prisoner) {
            return new PrisonerWrapper(prisoner.getUuid(), prisoner.getDetainedAt(), prisoner.getDuration(), prisoner.getDetainedBy());
        }
    }
}
