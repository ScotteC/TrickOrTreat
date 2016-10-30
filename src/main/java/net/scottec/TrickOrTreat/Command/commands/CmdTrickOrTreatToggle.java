package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
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
                sender.sendMessage("[ToT] TrickOrTreat: Deactivated");
            else
                sender.sendMessage("[ToT] TrickOrTreat: Activated");
        }
        else
            sender.sendMessage("No permission");
    }
}
