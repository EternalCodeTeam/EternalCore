package com.eternalcode.core.user.database;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class UserRepositoryConfig extends OkaeriConfig implements UserRepositorySettings {

    @Comment({
        "# Should plugin use batches to fetch users from the database?",
        "# We suggest turning this setting to TRUE for servers with more than 10k users",
        "# Set this to false if you are using SQLITE or H2 database (local databases)"
    })
    public boolean useBatchDatabaseFetching = false;

    @Comment({
        "# Size of batches querried to the database",
        "# Value must be greater than 0!"
    })
    public int batchDatabaseFetchSize = 1000;

    public Duration cacheLoadTreshold = Duration.ofDays(7);
}
