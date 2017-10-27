package net.scottec.trickortreat.command.commands.cmdtrick;

import net.scottec.trickortreat.command.CommandTop;
import net.scottec.trickortreat.TrickOrTreat;

public class CmdTrickTeleport extends CommandTop {

    public CmdTrickTeleport(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "teleport");
        this.setCommandUsage("/tot trick teleport <args> : Edit teleport targets");

        this.addSubCommand(new CmdTrickTeleportList(iToT));
        this.addSubCommand(new CmdTrickTeleportAdd(iToT));
        this.addSubCommand(new CmdTrickTeleportRemove(iToT));
    }
}
