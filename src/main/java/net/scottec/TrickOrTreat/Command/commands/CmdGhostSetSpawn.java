package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdGhostSetSpawn extends CommandBase {

    public CmdGhostSetSpawn(TrickOrTreat.ITrickOrTreat iToT){
        super(iToT, "setspawn");
        this.setCommandUsage("/tot ghost setspawn");
    }

    @Override
    public void execute(Player sender, List<String> args){
        if(sender.hasPermission("trickortreat.config")){
            this.plugin.getGhostHandler().setSpawnLocation(sender.getLocation());
            sender.sendMessage("[ToT] New ghostspawn set");
        }
    }
}
