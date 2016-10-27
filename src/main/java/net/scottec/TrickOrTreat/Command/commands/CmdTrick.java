package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandTop;
import net.scottec.TrickOrTreat.TrickOrTreat;

public class CmdTrick extends CommandTop {

    public CmdTrick(TrickOrTreat.ITrickOrTreat iToT){
        super(iToT, "trick");

        this.addSubCommand(new CmdTrickTeleport(iToT));
    }
}
