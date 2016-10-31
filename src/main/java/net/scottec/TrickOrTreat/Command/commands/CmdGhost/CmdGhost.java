package net.scottec.TrickOrTreat.Command.commands.CmdGhost;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

public class CmdGhost extends CommandTop {

    public CmdGhost(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "ghost");
        this.setCommandUsage("/tot ghost <args> : Handling ghosts");

        this.addSubCommand(new CmdGhostKill(iToT));
        this.addSubCommand(new CmdGhostSpawn(iToT));
        this.addSubCommand(new CmdGhostDrops(iToT));
    }
}
