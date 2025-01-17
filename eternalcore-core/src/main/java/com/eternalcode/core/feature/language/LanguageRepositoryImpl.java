package com.eternalcode.core.feature.language;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.database.DatabaseManager;
import com.eternalcode.core.database.wrapper.AbstractRepositoryOrmLite;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
class LanguageRepositoryImpl extends AbstractRepositoryOrmLite implements LanguageRepository {

    @Inject
    LanguageRepositoryImpl(DatabaseManager databaseManager, Scheduler scheduler) throws SQLException {
        super(databaseManager, scheduler);
        TableUtils.createTableIfNotExists(databaseManager.connectionSource(), LanguageTable.class);
    }

    @Override
    public CompletableFuture<Optional<Language>> findLanguage(UUID player) {
        return selectSafe(LanguageTable.class, player)
            .thenApply(optional -> optional.map(table -> table.toLanguage()));
    }

    @Override
    public CompletableFuture<Void> saveLanguage(UUID player, Language language) {
        return save(LanguageTable.class, new LanguageTable(player, language))
            .thenApply(status -> null);
    }

    @Override
    public CompletableFuture<Void> deleteLanguage(UUID player) {
        return deleteById(LanguageTable.class, player)
            .thenApply(result -> null);
    }

    @DatabaseTable(tableName = "eternal_core_languages")
    private static class LanguageTable {

        @DatabaseField(columnName = "id", id = true)
        private UUID player;

        @DatabaseField
        private String language;

        LanguageTable() {}

        LanguageTable(UUID player, Language language) {
            this.player = player;
            this.language = language.getLang();
        }

        Language toLanguage() {
            return Language.fromLocale(Locale.of(language));
        }
    }

}
