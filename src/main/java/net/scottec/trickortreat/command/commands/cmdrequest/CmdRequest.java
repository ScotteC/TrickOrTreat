package net.scottec.trickortreat.command.commands.cmdrequest;

import net.scottec.trickortreat.command.CommandTop;
import net.scottec.trickortreat.TrickOrTreat;

public class CmdRequest extends CommandTop {

    public CmdRequest(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "request");
        this.setCommandUsage("/tot request <args> : Handling requests");

        this.addSubCommand(new CmdRequestSetTiming(iToT));
    }
}
