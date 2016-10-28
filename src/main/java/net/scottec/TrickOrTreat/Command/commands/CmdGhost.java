package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

public class CmdGhost extends CommandTop {

    public CmdGhost(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "ghost");
        this.setCommandUsage("/tot ghost <args> : Handling ghosts");

        this.addSubCommand(new CmdGhostSetSpawn(iToT));
        this.addSubCommand(new CmdGhostKill(iToT));
    }
}
