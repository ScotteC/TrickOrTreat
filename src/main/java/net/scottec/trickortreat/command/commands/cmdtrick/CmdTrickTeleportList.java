package net.scottec.trickortreat.command.commands.cmdtrick;

import net.scottec.trickortreat.command.CommandBase;
import net.scottec.trickortreat.TrickOrTreat;
import net.scottec.trickortreat.tricks.Teleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdTrickTeleportList extends CommandBase {

    public CmdTrickTeleportList(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "list");
        this.setCommandUsage("/tot trick teleport list : List all TeleportLocations");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            List<Location> locations = ((Teleport) this.plugin.getTrickHandler().getTrickByName("teleport"))
                    .listPortLocations();
            // output
            sender.sendMessage("[ToT] Actual Teleports: [x/y/z/yaw/pitch]");
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
