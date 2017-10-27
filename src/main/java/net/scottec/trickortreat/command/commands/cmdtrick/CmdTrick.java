package net.scottec.trickortreat.command.commands.cmdtrick;

import net.scottec.trickortreat.command.CommandTop;
import net.scottec.trickortreat.TrickOrTreat;

public class CmdTrick extends CommandTop {

    public CmdTrick(TrickOrTreat.ITrickOrTreat iToT){
        super(iToT, "trick");
        this.setCommandUsage("/tot trick <args> : Handling tricks");

        this.addSubCommand(new CmdTrickTeleport(iToT));
    }
}
