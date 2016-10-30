package net.scottec.TrickOrTreat.Command.commands;

import net.scottec.TrickOrTreat.Command.CommandBase;
import net.scottec.TrickOrTreat.TrickOrTreat;
import net.scottec.TrickOrTreat.Tricks.Teleport;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdTrickTeleportAdd extends CommandBase{

    public CmdTrickTeleportAdd(TrickOrTreat.ITrickOrTreat iToT) {
        super(iToT, "add");
        this.setCommandUsage("/tot trick teleport add : Add actual position as TeleportLocation");
    }

    @Override
    public void execute(Player sender, List<String> args) {
        if (sender.hasPermission("trickortreat.config")) {
            ((Teleport) this.plugin.getTrickHandler().getTrickByName("teleport"))
                    .addPortLocation(sender.getLocation());
        }
        else
            sender.sendMessage("No permission");
    }
}
