package net.scottec.trickortreat.command.commands;

import net.scottec.trickortreat.command.CommandBase;
import net.scottec.trickortreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdTrickOrTreatToggle extends CommandBase {
    public CmdTrickOrTreatToggle(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "tottoggle");
        this.setCommandUsage("/tottoggle");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.toggle")) {
            boolean state = this.plugin.getRequestHandler().requestToggle(sender);
            if(state)
                sender.sendMessage("[ToT] trickortreat: Deactivated");
            else
                sender.sendMessage("[ToT] trickortreat: Activated");
        }
        else
            sender.sendMessage("No permission");
    }
}
