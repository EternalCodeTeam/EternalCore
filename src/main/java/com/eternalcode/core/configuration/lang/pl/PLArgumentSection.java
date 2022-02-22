package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class PLArgumentSection implements MessagesConfiguration.ArgumentSection {

    public String permissionMessage = "&4Blad: &cNie masz uprawnien do tej komendy! &7({PERMISSIONS})";
    public String offlinePlayer = "&4Blad: &cTen gracz jest offline!";
    public String onlyPlayer = "&4Blad: &cKomenda tylko dla graczy!";
    public String notNumber = "&4Blad: &cArgument nie jest liczba!";
    public String numberBiggerThanOrEqualZero = "&4Blad: &cLiczba musi byc rowna lub wieksza 0!";
    public String noItem = "&4Blad: &cMusisz miec item w rece!";
    public String noMaterial = "&4Blad: &cTaki material nie istenieje!";
    public String noArgument = "&4Blad: &cTaki argument nie istenieje!";

}
