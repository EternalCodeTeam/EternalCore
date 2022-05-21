package com.eternalcode.core.configuration.lang.tpa;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class PLTpaSection implements Messages.TpaSection {

    public String tpaSelfMessage = "&4Blad: &cNie mozesz siebie teleportowac!";
    public String tpaAlreadySentMessage = "&4Blad: &cWyslales juz prosbe o teleportacje!";
    public String tpaSentMessage = "&8» &aWyslales prosbe o teleportacje do gracza: &7{PLAYER}&a!";
    public String tpaRecivedMessage = "&8» &aOtrzymales prosbe o teleportacje od gracza: &7{PLAYER}&a! " +
        "\n &8» &6/tpaccept {PLAYER} &aaby zaakceptowac! " +
        "\n &8» &6/tpdeny {PLAYER} &aaby odrzucic!";

    public String tpaDenyNoRequestMessage = "&4Blad: &cNie masz prosby o teleportacje od tego gracza!";
    public String tpaDenyNoRequestMessageAll = "&4Blad: &cNie masz zadnych prosb o teleportacje!";
    public String tpaDenyDoneMessage = "&8» &cOdrzuciles prosbe o teleportacje od gracza: &7{PLAYER}&c!";
    public String tpaDenyRecivedMessage = "&8» &cGracz: {PLAYER} odrzucil twoja prosbe o teleportacje!";
    public String tpaDenyAllDenied = "&8» &cWszystkie prosby o teleportacje zostaly odrzucone!";

    public String tpaAcceptMessage = "&8» &aZaakceptowales teleportacje od gracza: &7{PLAYER}&a!";
    public String tpaAcceptNoRequestMessage = "&4Blad: &cTen gracz nie wyslal ci prosby o teleportacje!";
    public String tpaAcceptNoRequestAllMessage = "&4Blad: &cNie masz zadnych prosb o teleportacje!";
    public String tpaAcceptRecivedMessage = "&8» &aGracz: &7{PLAYER} &azaakceptowal twoja prosbe o teleportacje!";
    public String tpaAcceptAllAccepted = "&8» &aWszystkie prosby o teleportacje zostaly zaakceptowane!";

    @Override
    public String tpaSelfMessage() {
        return this.tpaSelfMessage;
    }

    @Override
    public String tpaAlreadySentMessage() {
        return this.tpaAlreadySentMessage;
    }

    @Override
    public String tpaSentMessage() {
        return this.tpaSentMessage;
    }

    @Override
    public String tpaRecivedMessage() {
        return this.tpaRecivedMessage;
    }

    @Override
    public String tpaDenyNoRequestMessage() {
        return this.tpaDenyNoRequestMessage;
    }

    @Override
    public String tpaDenyNoRequestMessageAll() {
        return this.tpaDenyNoRequestMessageAll;
    }

    @Override
    public String tpaDenyDoneMessage() {
        return this.tpaDenyDoneMessage;
    }

    @Override
    public String tpaDenyRecivedMessage() {
        return this.tpaDenyRecivedMessage;
    }

    @Override
    public String tpaDenyAllDenied() {
        return this.tpaDenyAllDenied;
    }

    @Override
    public String tpaAcceptMessage() {
        return this.tpaAcceptMessage;
    }

    @Override
    public String tpaAcceptNoRequestMessage() {
        return this.tpaAcceptNoRequestAllMessage;
    }

    @Override
    public String tpaAcceptNoRequestMessageAll() {
        return this.tpaAcceptNoRequestMessage;
    }

    @Override
    public String tpaAcceptRecivedMessage() {
        return this.tpaAcceptRecivedMessage;
    }

    @Override
    public String tpaAcceptAllAccepted() {
        return this.tpaAcceptAllAccepted;
    }
}
