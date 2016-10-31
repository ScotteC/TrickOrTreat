package net.scottec.TrickOrTreat.Command.commands.CmdRequest;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdRequestSetTiming extends CommandBase{

    public CmdRequestSetTiming(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "timing");
        this.setCommandUsage("/tot request timing <timeout> <cooldown> : Set timings for requests");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if(sender.hasPermission("trickortreat.config")) {
            if(args != null && args.size() >= 2){
                try {
                    this.plugin.getRequestHandler().setRequestTimings(
                            Integer.parseInt(args.get(0)),
                            Integer.parseInt(args.get(1)));
                } catch (NumberFormatException e) {
                    sender.sendMessage("Invalid input");
                }
            }
            else
                sender.sendMessage("Not enough arguments (2 needed)");
        }
        else
            sender.sendMessage("No Permission");
    }
}
