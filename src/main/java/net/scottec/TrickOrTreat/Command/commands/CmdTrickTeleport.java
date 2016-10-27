package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

public class CmdTrickTeleport extends CommandTop {

    public CmdTrickTeleport(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "teleport");

        this.addSubCommand(new CmdTrickTeleportAdd(iToT));
    }
}
