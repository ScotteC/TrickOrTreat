package net.scottec.trickortreat.command.commands.cmdghost;

import net.scottec.trickortreat.command.CommandBase;
import net.scottec.trickortreat.TrickOrTreat;
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
