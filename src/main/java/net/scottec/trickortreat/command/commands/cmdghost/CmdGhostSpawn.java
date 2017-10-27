package net.scottec.trickortreat.command.commands.cmdghost;

import net.scottec.trickortreat.command.CommandTop;
import net.scottec.trickortreat.TrickOrTreat;

public class CmdGhostSpawn extends CommandTop {

    public CmdGhostSpawn(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "spawn");
        this.setCommandUsage("/tot ghost spawn <args> : Handling ghost spawns");

        this.addSubCommand(new CmdGhostSpawnList(iToT));
        this.addSubCommand(new CmdGhostSpawnAdd(iToT));
        this.addSubCommand(new CmdGhostSpawnRemove(iToT));
        this.addSubCommand(new CmdGhostSpawnSetCount(iToT));
    }
}
