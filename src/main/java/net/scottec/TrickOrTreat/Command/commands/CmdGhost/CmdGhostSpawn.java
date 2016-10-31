package net.scottec.TrickOrTreat.Command.commands.CmdGhost;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

public class CmdGhostSpawn extends CommandTop {

    public CmdGhostSpawn(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "spawn");
        this.setCommandUsage("/tot ghost spawn <args> : Handling ghost spawns");

        this.addSubCommand(new CmdGhostSpawnList(iToT));
        this.addSubCommand(new CmdGhostSpawnAdd(iToT));
        this.addSubCommand(new CmdGhostSpawnRemove(iToT));
    }
}
