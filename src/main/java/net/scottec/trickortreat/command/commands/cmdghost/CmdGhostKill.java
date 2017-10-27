package net.scottec.trickortreat.command.commands.cmdghost;

import net.scottec.trickortreat.command.CommandBase;
import net.scottec.trickortreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdGhostKill extends CommandBase {
    public CmdGhostKill(TrickOrTreat.ITrickOrTreat iToT){
        super(iToT, "kill");
        this.setCommandUsage("/tot ghost kill : Kill all ghosts");
    }

    @Override
    public void execute(Player sender, List<String> args){
        if(sender.hasPermission("trickortreat.ghost.kill")){
            this.plugin.getGhostHandler().killAllGhosts();
            sender.sendMessage("[ToT] All ghosts killed");
        }
        else
            sender.sendMessage("No permission");
    }
}
