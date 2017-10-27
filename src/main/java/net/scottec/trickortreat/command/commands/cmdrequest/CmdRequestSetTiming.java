package net.scottec.trickortreat.command.commands.cmdrequest;

import net.scottec.trickortreat.command.CommandBase;
import net.scottec.trickortreat.TrickOrTreat;
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
                    sender.sendMessage("[ToT] Timings setted");
                } catch (NumberFormatException e) {
                    sender.sendMessage("[ToT] Invalid input");
                }
            }
            else {
                int[] timings = this.plugin.getRequestHandler().getRequestTimings();
                sender.sendMessage("[ToT] RequestTimeout: " + timings[0] + " - RequestCooldown: " + timings[1]);
            }
        }
        else
            sender.sendMessage("No Permission");
    }
}
