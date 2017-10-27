package net.scottec.trickortreat.command.commands;

import net.scottec.trickortreat.command.CommandTop;
import net.scottec.trickortreat.command.commands.cmdghost.CmdGhost;
import net.scottec.trickortreat.command.commands.cmdrequest.CmdRequest;
import net.scottec.trickortreat.command.commands.cmdtrick.CmdTrick;
import net.scottec.trickortreat.TrickOrTreat;

public class CmdTrickOrTreat extends CommandTop {

    public CmdTrickOrTreat(TrickOrTreat.ITrickOrTreat iToT){
        super(iToT, "tot");
        this.setCommandUsage("/tot <args> : Basecommand");

        this.addSubCommand(new CmdGhost(iToT));
        this.addSubCommand(new CmdTrick(iToT));
        this.addSubCommand(new CmdRequest(iToT));
    }
}
