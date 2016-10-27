package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdRequestSetCooldown extends CommandBase {

    public CmdRequestSetCooldown(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "cooldown");
        this.setCommandUsage("/tot request cooldown <seconds>");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if(sender.hasPermission("trickortreat.config")) {

        }
        else
            sender.sendMessage("No Permission");
    }
}
