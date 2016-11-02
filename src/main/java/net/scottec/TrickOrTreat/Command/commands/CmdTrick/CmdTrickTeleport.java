package net.scottec.TrickOrTreat.Command.commands.CmdTrick;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

public class CmdTrickTeleport extends CommandTop {

    public CmdTrickTeleport(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "teleport");
        this.setCommandUsage("/tot trick teleport <args> : Edit teleport targets");

        this.addSubCommand(new CmdTrickTeleportList(iToT));
        this.addSubCommand(new CmdTrickTeleportAdd(iToT));
        this.addSubCommand(new CmdTrickTeleportRemove(iToT));
    }
}