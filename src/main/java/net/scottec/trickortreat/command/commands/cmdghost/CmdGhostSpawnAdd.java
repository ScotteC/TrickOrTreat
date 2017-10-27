package net.scottec.trickortreat.command.commands.cmdghost;

import net.scottec.trickortreat.command.CommandBase;
import net.scottec.trickortreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdGhostSpawnAdd extends CommandBase{

    public CmdGhostSpawnAdd(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "add");
        this.setCommandUsage("/tot ghost spawn add : Add actual position as GhostSpawnLocation");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            this.plugin.getGhostHandler().addSpawnLocation(
                    sender.getLocation());
            sender.sendMessage("[ToT] GhostSpawn added");
        }
        else
            sender.sendMessage("No permission");
    }
}
