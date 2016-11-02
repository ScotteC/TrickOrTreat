package net.scottec.TrickOrTreat.Command.commands.CmdGhost;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdGhostSpawnSetCount extends CommandBase{

    public CmdGhostSpawnSetCount(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "count");
        this.setCommandUsage("/tot ghost spawn count <count>: Set number of ghosts spawning on PlayerJoin");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            if (args.size() > 0) {
                try {
                    if (this.plugin.getGhostHandler().setSpawnCount(
                            Integer.parseInt(args.get(0))))
                        sender.sendMessage("[ToT] SpawnCount edited");
                    else
                        sender.sendMessage("[ToT] Invalid value");
                } catch (NumberFormatException exp){
                    sender.sendMessage("[ToT] Invalid input");
                }
            }
            else
                sender.sendMessage("[ToT] SpawnCount: " + this.plugin.getGhostHandler().getSpawnCount());
        }
        else
            sender.sendMessage("No permission");
    }

}
