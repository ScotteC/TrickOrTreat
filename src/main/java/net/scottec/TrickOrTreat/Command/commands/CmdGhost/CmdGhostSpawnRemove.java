package net.scottec.TrickOrTreat.Command.commands.CmdGhost;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import net.scottec.TrickOrTreat.Tricks.Teleport;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdGhostSpawnRemove extends CommandBase {

    public CmdGhostSpawnRemove(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "remove");
        this.setCommandUsage("/tot ghost spawn remove <index>: Remove GhostSpawnLocation given by index");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            if (args.size() > 0) {
                try {
                    if (this.plugin.getGhostHandler().removeSpawnLocation(Integer.parseInt(args.get(0))))
                        sender.sendMessage("[ToT] Teleport removed");
                    else
                        sender.sendMessage("[ToT] Index not found");
                } catch (NumberFormatException exp){
                    sender.sendMessage("[ToT] Invalid index");
                }
            }
            else
                sender.sendMessage("[ToT] Not enough arguments");
        }
        else
            sender.sendMessage("No permission");
    }
}
