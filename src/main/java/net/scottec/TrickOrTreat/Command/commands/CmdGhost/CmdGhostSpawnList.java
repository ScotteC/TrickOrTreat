package net.scottec.TrickOrTreat.Command.commands.CmdGhost;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import net.scottec.TrickOrTreat.Tricks.Teleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdGhostSpawnList extends CommandBase {

    public CmdGhostSpawnList(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "list");
        this.setCommandUsage("/tot ghost spawn list : List all GhostSpawnLocations");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            List<Location> locations = this.plugin.getGhostHandler().listSpawnLocations();
            // output
            sender.sendMessage("[ToT] Actual GhostSpawns:");
            for (int i = 0; i < locations.size(); i++)
                sender.sendMessage(String.format("- %d : [%.2f / %.2f / %.2f / %.2f / %.2f]",
                        i,
                        locations.get(i).getX(),
                        locations.get(i).getY(),
                        locations.get(i).getZ(),
                        locations.get(i).getYaw(),
                        locations.get(i).getPitch()));
        }
        else
            sender.sendMessage("No permission");
    }
}
