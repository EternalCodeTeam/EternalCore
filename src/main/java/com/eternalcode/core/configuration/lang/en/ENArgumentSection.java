package com.eternalcode.core.configuration.lang.en;

import com.eternalcode.core.language.Messages;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class ENArgumentSection implements Messages.ArgumentSection {

    public String permissionMessage = "&8» &cYou don't have permission to perform this command! &7({PERMISSIONS})";
    public String offlinePlayer = "&8» &cThis player is currently offline!";
    public String onlyPlayer = "&8» &cCommand is only for players!";
    public String notNumber = "&8» &cArgument isnt a number!";
    public String numberBiggerThanOrEqualZero = "&8» &cThe number must be greater than or equal to 0!";
    public String noItem = "&8» &cYou need item to use this command!";
    public String noMaterial = "&8» &cThis item doesn't exist";
    public String noArgument = "&8» &cThis argument doesn't exist";

}
