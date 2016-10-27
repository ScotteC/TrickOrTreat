package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

/**
 * Created by Fabian on 23.10.2016.
 */
public class CmdRequest extends CommandTop {

    public CmdRequest(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "request");

        this.addSubCommand(new CmdRequestSetCooldown(iToT));
        this.addSubCommand(new CmdRequestSetTimeout(iToT));
    }
}
