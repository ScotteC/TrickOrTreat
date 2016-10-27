package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdRequestSetTimeout extends CommandBase{

    public CmdRequestSetTimeout(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "timeout");
        this.setCommandUsage("/tot request timeout <seconds>");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if(sender.hasPermission("trickortreat.config")) {

        }
        else
            sender.sendMessage("No Permission");
    }
}
