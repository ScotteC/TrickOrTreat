package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

public class CmdTrickOrTreat extends CommandTop {

    public CmdTrickOrTreat(TrickOrTreat.ITrickOrTreat iToT){
        super(iToT, "tot");
        this.setCommandUsage("/tot <args> : Basecommand");

        this.addSubCommand(new CmdGhost(iToT));
        this.addSubCommand(new CmdTrick(iToT));
        this.addSubCommand(new CmdRequest(iToT));
    }
}
