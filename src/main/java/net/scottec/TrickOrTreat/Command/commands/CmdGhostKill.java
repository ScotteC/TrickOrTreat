package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdGhostKill extends CommandBase {
    public CmdGhostKill(TrickOrTreat.ITrickOrTreat iToT){
        super(iToT, "kill");
        this.setCommandUsage("/tot ghost kill");
    }

    @Override
    public void execute(Player sender, List<String> args){
        if(sender.hasPermission("trickortreat.ghost.kill")){
            this.plugin.getGhost().killAllGhosts();
            sender.sendMessage("[ToT] All ghosts killed");
        }
        else
            sender.sendMessage("No permission");
    }
}
