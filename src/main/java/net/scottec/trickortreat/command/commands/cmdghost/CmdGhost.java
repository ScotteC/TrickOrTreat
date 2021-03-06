package net.scottec.trickortreat.command.commands.cmdghost;

import net.scottec.trickortreat.command.CommandTop;
import net.scottec.trickortreat.TrickOrTreat;

public class CmdGhost extends CommandTop {

    public CmdGhost(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "ghost");
        this.setCommandUsage("/tot ghost <args> : Handling ghosts");

        this.addSubCommand(new CmdGhostKill(iToT));
        this.addSubCommand(new CmdGhostSpawn(iToT));
        this.addSubCommand(new CmdGhostDrops(iToT));
    }
}
