package net.scottec.TrickOrTreat.Command.commands.CmdRequest;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

public class CmdRequest extends CommandTop {

    public CmdRequest(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "request");
        this.setCommandUsage("/tot request <args> : Handling requests");

        this.addSubCommand(new CmdRequestSetTiming(iToT));
    }
}
