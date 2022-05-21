package com.eternalcode.core.configuration.lang.tpa;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ENTpaSection implements Messages.TpaSection {

    public String tpaSelfMessage = "&8» &cYou can't teleport to yourself!";
    public String tpaAlreadySentMessage = "&8» &cYou have already sent a teleportation request!";
    public String tpaSentMessage = "&8» &aYou have sent a request for teleportation to a player: &7{PLAYER}&a!";
    public String tpaRecivedMessage = "&8» &aYou have received a request for teleportation from a player: &7{PLAYER}&a! " +
        "\n &8» &6/tpaccept {PLAYER} &ato accept! " +
        "\n &8» &6/tpdeny {PLAYER} &ato deny!";

    public String tpaDenyNoRequestMessage = "&8» &cYou do not have a teleport request from this player!";
    public String tpaDenyNoRequestAllMessage = "&8» &cYou do not have a teleport requests!";
    public String tpaDenyDoneMessage = "&8» &cYou have declined a teleport request from a player: &7{PLAYER}&c!";
    public String tpaDenyRecivedMessage = "&8» &cPlayer: &7{PLAYER} rejected your teleport request!";
    public String tpaDenyAllDenied = "&8» &cAll players have denied your teleport request!";

    public String tpaAcceptMessage = "&8» &aYou have accepted the teleportation from the player: &7{PLAYER}&a!";
    public String tpaAcceptNoRequestMessage = "&8» &cThis player has not sent you a teleport request!";
    public String tpaAcceptNoRequestAllMessage = "&8» &cYou do not have a teleport requests!";
    public String tpaAcceptRecivedMessage = "&8» &aPlayer: &7{PLAYER} &aaccepted your teleportation request!";
    public String tpaAcceptAllAccepted = "&8» &aAll players have accepted your teleport request!";

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
        return this.tpaDenyNoRequestAllMessage;
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
        return this.tpaAcceptNoRequestMessage;
    }

    @Override
    public String tpaAcceptNoRequestMessageAll() {
        return this.tpaAcceptNoRequestAllMessage;
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
