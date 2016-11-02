package net.scottec.TrickOrTreat.Command.commands.CmdGhost;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdGhostDrops extends CommandBase {

    public CmdGhostDrops(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "drops");
        this.setCommandUsage("/tot ghost drops <count> <delay>: Parameter for DropTask, delay in Ticks");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            if (args.size() > 1) {
                try {
                    if (this.plugin.getGhostHandler().setDrops(
                            Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1))))
                        sender.sendMessage("[ToT] DropTask edited");
                    else
                        sender.sendMessage("[ToT] Invalid value");
                } catch (NumberFormatException exp){
                    sender.sendMessage("[ToT] Invalid input");
                }
            }
            else {
                int[] drops = this.plugin.getGhostHandler().getDrops();
                sender.sendMessage("[ToT] DropCount: " + drops[0] + " - DropDelay: " + drops[1]);
            }
        }
        else
            sender.sendMessage("No permission");
    }
}
